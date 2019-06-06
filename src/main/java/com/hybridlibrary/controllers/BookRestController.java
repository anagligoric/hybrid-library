package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookRestController {

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<BookDto>> findAll() {

        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getOne(@PathVariable("id") Long id) {

        return ResponseEntity.ok(bookService.getOne(id));

    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(bookService.getByTitle(title));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDto>> getByAuthor(@PathVariable("author") String author) {
        return ResponseEntity.ok(bookService.getByAuthor(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.delete(id));
    }

    @PostMapping("")
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.create(bookDto));
    }

    @PutMapping("")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }

}
