package com.hybridlibrary.controllers;

import com.hybridlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hybridlibrary.models.Book;

import java.util.Collection;

@RestController
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("book")
    public ResponseEntity<?> getBooks(){

        Collection<Book> books = bookService.getAllBooks();
        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }


    }

    @GetMapping("book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Integer id){

        Book book = bookService.getBook(id);
        //if(!book.getId())

        return ResponseEntity.ok(book);

    }
    @GetMapping("bookName/{name}")
    public ResponseEntity<?> getBookByName(@PathVariable("name") String name){

        Collection<Book> books = bookService.getBookByName(name);

        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }

    }
    @GetMapping("bookAuthor/{author}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable("author") String author){

        Collection<Book> books = bookService.getBookByAuthor(author);

        if(!books.isEmpty()) {

            return ResponseEntity.ok(books);
        }
        else{

            return  ResponseEntity.noContent().build();

        }
    }
    @DeleteMapping(value = "book/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable ("id") Integer id){

        if(!bookService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "book")
    public ResponseEntity<Book> createBook (@RequestBody Book book){
        if(!bookService.existById(book.getId())){
            bookService.createBook(book);
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
            bookService.updateBook(book);
            return ResponseEntity.ok(book);
        }
    }

}
