package com.hybridlibrary.services;

import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.repositories.BookCopyRepository;
import com.hybridlibrary.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hybridlibrary.models.Book;

import java.util.Collection;

@Service
public class BookCopyService {
    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    public Collection<BookCopy> getAllBookCopies(){

        return bookCopyRepository.findAll();
    }

    public BookCopy getBookCopy(Integer id){

        return bookCopyRepository.getOne(id);
    }

    public Collection<BookCopy> getCopiesByBook(Integer id){

        Book book = bookRepository.getOne(id);
        return bookCopyRepository.findByBook(book);

    }

    public Integer getCopiesCount(Integer id){
        Book book = bookRepository.getOne(id);
        return bookCopyRepository.countByBook(book);
    }

    public BookCopy updateBookCopy(BookCopy bookCopy){
        bookCopyRepository.save(bookCopy);
        return bookCopy;
    }
    public BookCopy createBookCopy(BookCopy bookCopy){
        bookCopyRepository.save(bookCopy);
        return bookCopy;
    }
    public void deleteBookCopy(Integer id){
        bookCopyRepository.deleteById(id);
    }
    public boolean existById(Integer id){
        return bookCopyRepository.existsById(id);
    }
}
