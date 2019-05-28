package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRentalRepository;
import com.hybridlibrary.services.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookRentalServiceImpl implements BookRentalService {
    @Autowired
    private BookRentalRepository bookRentalRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Override
    public Collection<BookRental> findAll() {
        return bookRentalRepository.findAll();
    }

    @Override
    public BookRental getOne(Long id) {
        return bookRentalRepository.getOne(id);
    }

    @Override
    public BookRental update(BookRental book) {
        bookRentalRepository.save(book);
        return book;
    }

    @Override
    public BookRental create(BookRental bookRental) {
        BookCopy bookCopy = bookRental.getBookCopy();
        Book book = bookCopy.getBook();
        return bookRentalRepository.saveAndFlush(bookRental);
    }

    @Override
    public void delete(Long id) {
        bookRentalRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return bookRentalRepository.existsById(id);
    }

    @Override
    public Collection<BookRental> getByBookCopy(Long id) {
        BookCopy bookCopy = bookCopyRepository.getOne(id);
        return bookRentalRepository.getByBookCopy(bookCopy);
    }

    @Override
    public Integer countByBookCopy(Long id) {
        BookCopy bookCopy = bookCopyRepository.getOne(id);
        return bookRentalRepository.countByBookCopy(bookCopy);
    }


}
