package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.services.BookCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book/{bookId}/bookcopy")
@Slf4j
public class BookCopyRestController {
    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("")
    public ResponseEntity<List<BookCopyDto>> findAll(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(bookCopyService.getByBook(bookId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDto> getOne(@PathVariable("bookId") Long bookId, @PathVariable("id") Long id) {
        return ResponseEntity.ok(bookCopyService.findByBookAndId(bookId, id));
    }

    @PostMapping("")
    public ResponseEntity<BookCopyDto> create(@PathVariable Long bookId, @RequestBody BookCopyDto bookCopyDto) {
        return ResponseEntity.ok(bookCopyService.create(bookCopyDto, bookId));
    }

    @PutMapping("")
    public ResponseEntity<BookCopyDto> update(@RequestBody BookCopyDto bookCopyDto) {
        return ResponseEntity.ok(bookCopyService.update(bookCopyDto));
    }
}
