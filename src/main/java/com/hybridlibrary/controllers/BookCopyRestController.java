package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.services.BookCopyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@Slf4j
public class BookCopyRestController {
    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("bookcopy")
    public ResponseEntity<List<BookCopyDto>> findAll() {
        Collection<BookCopy> bookCopies = bookCopyService.findAll();
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        if (CollectionUtils.isEmpty(bookCopies)) {
            return ResponseEntity.noContent().build();

        } else {
            Iterator<BookCopy> it = bookCopies.iterator();
            while (it.hasNext()) {
                bookCopyList.add(conversionService.convert(it.next(), BookCopyDto.class));
            }
            log.info("Book copies fetched");

            return ResponseEntity.ok(bookCopyList);
        }
    }

    @GetMapping("bookcopy/{id}")
    public ResponseEntity<BookCopyDto> getOne(@PathVariable("id") Long id) {
        if (bookCopyService.existById(id)) {
            BookCopy bookCopy = bookCopyService.getOne(id);
            BookCopyDto bookCopyDto = conversionService.convert(bookCopy, BookCopyDto.class);
            log.info("Book copy with id {} is listed.", id);
            return ResponseEntity.ok(bookCopyDto);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("bookcopies/{id}")
    public ResponseEntity<List<BookCopyDto>> getByBook(@PathVariable("id") Long id) {
        Collection<BookCopy> bookCopies = bookCopyService.getByBook(id);
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        if (CollectionUtils.isEmpty(bookCopies)) {
            return ResponseEntity.noContent().build();

        } else {
            Iterator<BookCopy> it = bookCopies.iterator();
            while (it.hasNext()) {
                bookCopyList.add(conversionService.convert(it.next(), BookCopyDto.class));
            }
            log.info("Copies od the book with id {} are listed.", id);
            return ResponseEntity.ok(bookCopyList);
        }
    }

    @DeleteMapping(value = "bookcopy/{id}")
    public ResponseEntity<BookCopyDto> delete(@PathVariable("id") Long id) {

        if (bookCopyService.existById(id)) {
            BookCopy bookCopy = bookCopyService.getOne(id);
            BookCopyDto bookCopyDto = conversionService.convert(bookCopy, BookCopyDto.class);
            bookCopyService.delete(id);
            log.info("Book copy with id {} is deleted.", id);
            return ResponseEntity.ok(bookCopyDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "bookcopy")
    public ResponseEntity<BookCopyDto> create(@RequestBody BookCopyDto bookCopyDto) {
        try {

            BookCopy bookCopy = conversionService.convert(bookCopyDto, BookCopy.class);
            bookCopyService.create(bookCopy);
            return ResponseEntity.ok(bookCopyDto);
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }


    }

    @PutMapping(value = "bookcopy")
    public ResponseEntity<BookCopyDto> update(@RequestBody BookCopy bookCopy) {
        try {
            if (bookCopyService.existById(bookCopy.getId())) {
                bookCopyService.update(bookCopy);
                BookCopyDto bookCopyDto = conversionService.convert(bookCopy, BookCopyDto.class);
                return ResponseEntity.ok(bookCopyDto);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }

    }
}
