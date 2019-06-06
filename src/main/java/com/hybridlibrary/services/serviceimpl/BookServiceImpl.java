package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.services.BookService;
import com.hybridlibrary.utils.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private ConversionService conversionService;

    private BookRepository bookRepository;

    private BookRentalRepository bookRentalRepository;

    private BookCopyRepository bookCopyRepository;

    @Autowired
    public BookServiceImpl(ConversionService conversionService, BookRepository bookRepository, BookRentalRepository bookRentalRepository, BookCopyRepository bookCopyRepository) {
        this.conversionService = conversionService;
        this.bookRepository = bookRepository;
        this.bookRentalRepository = bookRentalRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    @Override
    public List<BookDto> findAll() {
        List<BookDto> bookList = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            BookDto bookDto = conversionService.convert(book, BookDto.class);
            bookList.add(bookDto);
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
            Book book = bookRepository.getOne(id);
            return conversionService.convert(book, BookDto.class);
        } else {
            throw new NotFoundException(ApplicationConstants.BOOK + id + ApplicationConstants.NOT_FOUND);
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
            throw new NotFoundException(ApplicationConstants.BOOK + bookDto.getId() + ApplicationConstants.NOT_FOUND);
        }
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = conversionService.convert(bookDto, Book.class);
        log.info("{} is added.", book);
        Book newBook = bookRepository.save(book);
        return conversionService.convert(newBook, BookDto.class);
    }

    @Transactional
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
            throw new NotFoundException(ApplicationConstants.BOOK + id + ApplicationConstants.NOT_FOUND);
        }

    }

    @Override
    public boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

}
