package com.hybridlibrary.controllers;

import com.hybridlibrary.models.BookCopy;

import com.hybridlibrary.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class BookCopyRestController {
    @Autowired
    private BookCopyService bookCopyService;

    @GetMapping("bookCopy")
    public ResponseEntity<?> getBookCopies() {
        Collection<BookCopy> bookCopies = bookCopyService.findAll();
        if(CollectionUtils.isEmpty(bookCopies)){
            return ResponseEntity.noContent().build();

        }else{
            return ResponseEntity.ok(bookCopies);
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
        if(CollectionUtils.isEmpty(bookCopies)){
            return ResponseEntity.noContent().build();

        }else{
            return ResponseEntity.ok(bookCopies);
        }
    }

    /*@GetMapping("copiesCount/{id}")
    public ResponseEntity<?> getCopiesCount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookCopyService.getCopiesCount(id));
    }*/
    @DeleteMapping(value = "bookCopy/{id}")
    public ResponseEntity<Void> deleteBookCopy(@PathVariable ("id") Long id){

        if(bookCopyService.existById(id)){
            bookCopyService.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "bookCopy")
    public ResponseEntity<BookCopy> createBookCopy (@RequestBody BookCopy bookCopy){
        if(bookCopyService.existById(bookCopy.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }else{
            BookCopy newBookCopy = bookCopyService.create(bookCopy);
            return ResponseEntity.ok(newBookCopy);
        }
    }
    @PutMapping(value = "bookCopy")
    public ResponseEntity<BookCopy> updateBookCopy (@RequestBody BookCopy bookCopy){
        if(bookCopyService.existById(bookCopy.getId())){
            bookCopyService.update(bookCopy);
            return ResponseEntity.ok(bookCopy);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
