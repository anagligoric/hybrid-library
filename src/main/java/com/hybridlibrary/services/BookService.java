package com.hybridlibrary.services;

import com.hybridlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.hybridlibrary.models.Book;

import java.util.Collection;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Collection<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Book getBook(Integer id){
        return bookRepository.getOne(id);
    }

    public Collection<Book> getBookByName(String name){
        return bookRepository.findByNameContainingIgnoreCase(name);
    }
    public Collection<Book> getBookByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public Book updateBook(Book book){
        bookRepository.save(book);
        return book;
    }
    public Book createBook(Book book){
        bookRepository.save(book);
        return book;
    }
    public void deleteBook(Integer id){
        bookRepository.deleteById(id);
    }
    public boolean existById(Integer id){
        return bookRepository.existsById(id);
    }
}
