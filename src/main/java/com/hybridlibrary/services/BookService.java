package com.hybridlibrary.services;

import com.hybridlibrary.models.Book;

import java.util.Collection;


public interface BookService extends AbstractService<Book, Long>{

    Collection<Book> findAll();
    Book getOne(Long id);
    Collection<Book> getByTitle(String title);
    Collection<Book> getByAuthor(String author);
    Book update(Book book);
    Book create(Book book);
    void delete(Long id);
    boolean existById(Long id);


}
