package com.hybridlibrary.controllers;

import com.hybridlibrary.models.BookCopy;

import com.hybridlibrary.services.serviceImpl.BookCopyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookCopyRestController {
    @Autowired
    private BookCopyServiceImpl bookCopyService;

    @GetMapping("bookCopy")
    public ResponseEntity<?> getBookCopies() {
        Collection<BookCopy> bookCopies = bookCopyService.findAll();
        if (!bookCopies.isEmpty()) {

            return ResponseEntity.ok(bookCopies);
        } else {

            return ResponseEntity.noContent().build();

        }
    }

    @GetMapping("bookCopy/{id}")
    public ResponseEntity<?> getBookCopy(@PathVariable("id") Long id) {
        BookCopy bookCopy = bookCopyService.getOne(id);
        return ResponseEntity.ok(bookCopy);
    }

    @GetMapping("copiesByBook/{id}")
    public ResponseEntity<?> getCopiesForBook(@PathVariable("id") Long id) {
        Collection<BookCopy> bookCopies = bookCopyService.getByBook(id);
        if (!bookCopies.isEmpty()) {
            return ResponseEntity.ok(bookCopies);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("copiesCount/{id}")
    public ResponseEntity<?> getCopiesCount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookCopyService.getCopiesCount(id));
    }
    @DeleteMapping(value = "bookCopy/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable ("id") Long id){

        if(!bookCopyService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            bookCopyService.delete(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "bookCopy")
    public ResponseEntity<BookCopy> createBookCopy (@RequestBody BookCopy bookCopy){
        if(!bookCopyService.existById(bookCopy.getId())){
            bookCopyService.create(bookCopy);
            return ResponseEntity.ok(bookCopy);
        }else{
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PutMapping(value = "bookCopy")
    public ResponseEntity<BookCopy> updateBookCopy (@RequestBody BookCopy bookCopy){
        if(!bookCopyService.existById(bookCopy.getId())){
            return ResponseEntity.noContent().build();
        }else{
            bookCopyService.update(bookCopy);
            return ResponseEntity.ok(bookCopy);
        }
    }
}
