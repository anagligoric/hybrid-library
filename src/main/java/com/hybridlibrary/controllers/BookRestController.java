package com.hybridlibrary.controllers;

import com.hybridlibrary.models.BookDto;
import com.hybridlibrary.services.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hybridlibrary.models.Book;

import java.util.Collection;

@RestController
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("book")
    public ResponseEntity<?> getBooks(){

        Collection<Book> books = bookService.findAll();
        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(books);

        }
    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id){

        Book book = bookService.getOne(id);
        ModelMapper modelMapper = new ModelMapper();
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        //if(!book.getId())

        return ResponseEntity.ok(bookDto);

    }
    @GetMapping("bookName/{name}")
    public ResponseEntity<?> getBookByName(@PathVariable("name") String name){

        Collection<Book> books = bookService.getByTitle(name);

        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(books);

        }

    }
    @GetMapping("bookAuthor/{author}")
    public ResponseEntity<?> getBookByAuthor(@PathVariable("author") String author){

        Collection<Book> books = bookService.getByAuthor(author);

        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(books);

        }
    }
    @DeleteMapping(value = "book/{id}")
        public ResponseEntity<Void> deleteBook(@PathVariable ("id") Long id){

        if(bookService.existById(id)){
            bookService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "book")
    public ResponseEntity<Book> createBook (@RequestBody Book book){

        if(bookService.existById(book.getId())){

            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }else{
            Book newBook = bookService.create(book);
            return ResponseEntity.ok(newBook);
        }
    }
    @PutMapping(value = "book")
    public ResponseEntity<Book> updateBook (@RequestBody Book book){
        if(bookService.existById(book.getId())){
            bookService.update(book);
            return ResponseEntity.ok(book);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
