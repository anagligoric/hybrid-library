package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.exception.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public List<BookDto> findAll() {
        List<BookDto> bookList = new ArrayList<>();
        for (Book book :
                bookRepository.findAll()) {
            bookList.add(conversionService.convert(book, BookDto.class));
        }
        if (CollectionUtils.isEmpty(bookList)) {
            throw new NotFoundException("There is no any books.");
        } else {
            log.info("Books fetched");
            return bookList;
        }
    }

    @Override
    public BookDto getOne(Long id) {
        if (bookRepository.existsById(id)) {
            log.info("Book with id {} is listed", id);
            return conversionService.convert(bookRepository.getOne(id), BookDto.class);
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
    }

    @Override
    public List<BookDto> getByTitle(String title) {
        List<BookDto> bookList = new ArrayList<>();
        for (Book book :
                bookRepository.findByTitleContainingIgnoreCase(title)) {
            bookList.add(conversionService.convert(book, BookDto.class));
        }
        if (CollectionUtils.isEmpty(bookList)) {
            throw new NotFoundException("There is no any books which title contains " + title);
        } else {
            log.info("Books which title contains '{}' are listed", title);
            return bookList;
        }
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        List<BookDto> bookList = new ArrayList<>();
        for (Book book :
                bookRepository.findByAuthorContainingIgnoreCase(author)) {
            bookList.add(conversionService.convert(book, BookDto.class));
        }
        if (CollectionUtils.isEmpty(bookList)) {
            throw new NotFoundException("There is no any books which author contains " + author);
        } else {
            log.info("Books which author contains '{}' are listed", author);
            return bookList;
        }
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        if (bookRepository.existsById(book.getId())) {
            log.info("{} is updated.", book);
            return conversionService.convert(bookRepository.save(book), BookDto.class);
        } else {
            throw new NotFoundException("Book with id " + bookDto.getId() + " not found");
        }
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        log.info("{} is added.", book);
        return conversionService.convert(bookRepository.save(book), BookDto.class);

    }

    @Override
    public BookDto delete(Long id) {
        if (bookRepository.existsById(id)) {
            Book book = bookRepository.getOne(id);
            if (CollectionUtils.isEmpty(bookCopyRepository.findByBook(book))) {
                log.info("Book with id {} is deleted.", id);
                bookRepository.deleteById(id);
                return conversionService.convert(book, BookDto.class);
            } else if (bookCopyRepository.countByBook(book).equals(
                    bookCopyRepository.countByBookAndRentedEquals(book, false)
            )) {
                log.info("Book with id {} is deleted", id);
                bookRentalRepository.deleteByBook(book);
                bookCopyRepository.deleteByBook(book);
                bookRepository.deleteById(id);
                return conversionService.convert(book, BookDto.class);

            } else {
                log.warn("Book with id {} can not be deleted", id);
                throw new IllegalArgumentException("Book can not be deleted, it has rented copies");
            }
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }

    }

    @Override
    public boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

}
