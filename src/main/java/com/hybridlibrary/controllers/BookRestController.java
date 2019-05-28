package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@ControllerAdvice
public class BookRestController {


    @Autowired
    private ConversionService conversionService;

    @Autowired
    private BookService bookService;

    @GetMapping("book")
    public ResponseEntity<List<BookDto>> findAll() {


        Collection<Book> books = bookService.findAll();
        List<BookDto> bookList = new ArrayList<>();
        if (CollectionUtils.isEmpty(books)) {
            return ResponseEntity.noContent().build();
        } else {
            for (Book book :
                    books) {
                bookList.add(conversionService.convert(book, BookDto.class));
            }
            log.info("Books fetched");

            return ResponseEntity.ok(bookList);

        }


    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookDto> getOne(@PathVariable("id") Long id) {
        if (bookService.existById(id)) {
            Book book = bookService.getOne(id);
            BookDto bookDto = conversionService.convert(book, BookDto.class);
            log.info("Book with id {} is listed", id);
            return ResponseEntity.ok(bookDto);
        } else {
            return ResponseEntity.noContent().build();
        }


    }

    @GetMapping("booktitle/{title}")
    public ResponseEntity<List<BookDto>> getByTitle(@PathVariable("title") String title) {

        Collection<Book> books = bookService.getByTitle(title);
        List<BookDto> bookList = new ArrayList<>();

        if (CollectionUtils.isEmpty(books)) {
            return ResponseEntity.noContent().build();
        } else {
            for (Book book :
                    books) {
                bookList.add(conversionService.convert(book, BookDto.class));
            }
            log.info("Books which title contains '{}' are listed.", title);
            return ResponseEntity.ok(bookList);

        }

    }

    @GetMapping("bookauthor/{author}")
    public ResponseEntity<List<BookDto>> getByAuthor(@PathVariable("author") String author) {

        Collection<Book> books = bookService.getByAuthor(author);
        List<BookDto> bookList = new ArrayList<>();

        if (CollectionUtils.isEmpty(books)) {
            return ResponseEntity.noContent().build();
        } else {
            for (Book book :
                    books) {
                bookList.add(conversionService.convert(book, BookDto.class));
            }
            log.info("Books which author contains '{}' are listed.", author);
            return ResponseEntity.ok(bookList);

        }
    }

    @DeleteMapping(value = "book/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") Long id) {
        if (bookService.existById(id)) {
            Book book = bookService.getOne(id);
            BookDto bookDto = conversionService.convert(book, BookDto.class);
            bookService.delete(id);
            log.info("Book with id {} is deleted.", id);
            return ResponseEntity.ok(bookDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "book")
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {

        try {
            Book book = conversionService.convert(bookDto, Book.class);
            bookService.create(book);
            log.info("{} is added.", bookDto);
            return ResponseEntity.ok(bookDto);
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value = "book")
    public ResponseEntity<BookDto> update(@RequestBody Book book) {
        try {
            if (bookService.existById(book.getId())) {
                bookService.update(book);
                BookDto bookDto = conversionService.convert(book, BookDto.class);
                log.info("{} is updated.", bookDto);
                return ResponseEntity.ok(bookDto);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }

    }

}
