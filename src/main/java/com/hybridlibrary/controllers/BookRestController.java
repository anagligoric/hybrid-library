package com.hybridlibrary.controllers;

import com.hybridlibrary.services.BookService;
import com.hybridlibrary.services.serviceImpl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hybridlibrary.models.Book;

import java.util.Collection;

@RestController
public class BookRestController {

    @Autowired
    private BookServiceImpl bookService;

    @GetMapping("book")
    public ResponseEntity<?> getBooks(){

        Collection<Book> books = bookService.findAll();
        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }


    }

    @GetMapping("book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id){

        Book book = bookService.getOne(id);
        //if(!book.getId())

        return ResponseEntity.ok(book);

    }
    @GetMapping("bookName/{name}")
    public ResponseEntity<?> getBookByName(@PathVariable("name") String name){

        Collection<Book> books = bookService.getByTitle(name);

        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }

    }
    @GetMapping("bookAuthor/{author}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable("author") String author){

        Collection<Book> books = bookService.getByAuthor(author);

        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }
    }
    @DeleteMapping(value = "book/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable ("id") Long id){

        if(!bookService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "book")
    public ResponseEntity<Book> createBook (@RequestBody Book book){
        if(!bookService.existById(book.getId())){
            bookService.create(book);
            return ResponseEntity.ok(book);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping(value = "book")
    public ResponseEntity<Book> updateBook (@RequestBody Book book){
        if(!bookService.existById(book.getId())){
            return ResponseEntity.noContent().build();
        }else{
            bookService.update(book);
            return ResponseEntity.ok(book);
        }
    }

}
