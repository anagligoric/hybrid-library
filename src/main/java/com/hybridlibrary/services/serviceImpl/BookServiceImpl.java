package com.hybridlibrary.services.serviceImpl;

import com.hybridlibrary.models.Book;
import com.hybridlibrary.repositories.BookRepository;
import com.hybridlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository bookRepository;

    @Override
    public Collection<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getOne(Long id) {
        return bookRepository.getOne(id);
    }

    @Override
    public Collection<Book> getByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);

    }

    @Override
    public Collection<Book> getByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Override
    public Book update(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book create(Book book) {
        bookRepository.save(book);
        return book;
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean existById(Long id) {
        return bookRepository.existsById(id);
    }

}
