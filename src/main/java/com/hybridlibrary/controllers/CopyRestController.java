package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.services.BookCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bookcopy")
@Slf4j
public class CopyRestController {

    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("")
    public ResponseEntity<List<BookCopyDto>> findAll() {
        return ResponseEntity.ok(bookCopyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookCopyDto> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookCopyService.getOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookCopyDto> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookCopyService.delete(id));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BookCopyDto>> getOverdueBooks(){
        return ResponseEntity.ok(bookCopyService.overdueBookReturns());
    }


}
