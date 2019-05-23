package com.hybridlibrary.controllers;

import com.hybridlibrary.models.BookCopy;

import com.hybridlibrary.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookCopyRestController {
    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("bookCopy")
    public ResponseEntity<?> getBookCopies() {
        Collection<BookCopy> bookCopies = bookCopyService.getAllBookCopies();
        if (!bookCopies.isEmpty()) {

            return ResponseEntity.ok(bookCopies);
        } else {

            return ResponseEntity.noContent().build();

        }
    }

    @GetMapping("bookCopy/{id}")
    public ResponseEntity<?> getBookCopy(@PathVariable("id") Integer id) {
        BookCopy bookCopy = bookCopyService.getBookCopy(id);
        return ResponseEntity.ok(bookCopy);
    }

    @GetMapping("copiesByBook/{id}")
    public ResponseEntity<?> getCopiesForBook(@PathVariable("id") Integer id) {
        Collection<BookCopy> bookCopies = bookCopyService.getCopiesByBook(id);
        if (!bookCopies.isEmpty()) {
            return ResponseEntity.ok(bookCopies);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("copiesCount/{id}")
    public ResponseEntity<?> getCopiesCount(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bookCopyService.getCopiesCount(id));
    }
    @DeleteMapping(value = "bookCopy/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable ("id") Integer id){

        if(!bookCopyService.existById(id)){
            return ResponseEntity.noContent().build();
        }else{
            bookCopyService.deleteBookCopy(id);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping(value = "bookCopy")
    public ResponseEntity<BookCopy> createBookCopy (@RequestBody BookCopy bookCopy){
        if(!bookCopyService.existById(bookCopy.getId())){
            bookCopyService.createBookCopy(bookCopy);
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
            bookCopyService.updateBookCopy(bookCopy);
            return ResponseEntity.ok(bookCopy);
        }
    }
}
