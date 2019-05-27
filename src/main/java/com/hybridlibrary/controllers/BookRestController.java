package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import com.hybridlibrary.models.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
public class BookRestController {


    @Autowired
    private ConversionService conversionService;

    @Autowired
    private BookService bookService;

    @GetMapping("book")
    public ResponseEntity<List<BookDto>> findAll(){

        Collection<Book> books = bookService.findAll();
        List<BookDto> bookList = new ArrayList<>();
        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            Iterator<Book> it = books.iterator();
            while(it.hasNext()){
                bookList.add(conversionService.convert(it.next(), BookDto.class));
            }
            return ResponseEntity.ok(bookList);

        }
    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookDto> getOne(@PathVariable("id") Long id){
        Book book = bookService.getOne(id);
        BookDto bookDto = conversionService.convert(book, BookDto.class);
        return ResponseEntity.ok(bookDto);

    }
    @GetMapping("bookname/{name}")
    public ResponseEntity<List<BookDto>> getByTitle(@PathVariable("title") String title){

        Collection<Book> books = bookService.getByTitle(title);
        List<BookDto> bookList = new ArrayList<>();

        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            Iterator<Book> it = books.iterator();
            while(it.hasNext()){
                bookList.add(conversionService.convert(it.next(), BookDto.class));
            }
            return ResponseEntity.ok(bookList);

        }

    }
    @GetMapping("bookauthor/{author}")
    public ResponseEntity<List<BookDto>> getByAuthor(@PathVariable("author") String author){

        Collection<Book> books = bookService.getByAuthor(author);
        List<BookDto> bookList = new ArrayList<>();

        if(CollectionUtils.isEmpty(books)){
            return  ResponseEntity.noContent().build();
        }else{
            Iterator<Book> it = books.iterator();
            while(it.hasNext()){
                bookList.add(conversionService.convert(it.next(), BookDto.class));
            }
            return ResponseEntity.ok(bookList);

        }
    }
    @DeleteMapping(value = "book/{id}")
        public ResponseEntity<BookDto> delete(@PathVariable ("id") Long id){

        if(bookService.existById(id)){
            Book book = bookService.getOne(id);
            BookDto bookDto = conversionService.convert(book, BookDto.class);
            bookService.delete(id);
            return ResponseEntity.ok(bookDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "book")
    public ResponseEntity<BookDto> create (@RequestBody BookDto bookDto){

            Book book = conversionService.convert(bookDto, Book.class);
            bookService.create(book);
            return ResponseEntity.ok(bookDto);

    }
    @PutMapping(value = "book")
    public ResponseEntity<BookDto> update (@RequestBody BookDto bookDto){
        Book book = conversionService.convert(bookDto, Book.class);
        if(bookService.existById(book.getId())){
            bookService.update(book);
            return ResponseEntity.ok(bookDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

}
