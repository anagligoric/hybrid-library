package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
@ControllerAdvice
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

        if (CollectionUtils.isEmpty(bookService.getByTitle(title))) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(bookService.getByTitle(title));
        }
    }

    @RequestMapping(value = "/author/{author}", method = RequestMethod.GET)
    public ResponseEntity<List<BookDto>> getByAuthor(@PathVariable("author") String author) {

        return ResponseEntity.ok(bookService.getByAuthor(author));

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.delete(id));

    }

    @PostMapping("")
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {

        try {
            return ResponseEntity.ok(bookService.create(bookDto));
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        try {
            return ResponseEntity.ok(bookService.update(bookDto));
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }

    }

}
