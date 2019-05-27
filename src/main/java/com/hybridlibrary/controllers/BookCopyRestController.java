package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookCopyDto;
import com.hybridlibrary.models.BookCopy;

import com.hybridlibrary.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
public class BookCopyRestController  {
    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("bookcopy")
    public ResponseEntity<List<BookCopyDto>> findAll() {
        Collection<BookCopy> bookCopies = bookCopyService.findAll();
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        if(CollectionUtils.isEmpty(bookCopies)){
            return ResponseEntity.noContent().build();

        }else{
            Iterator<BookCopy> it = bookCopies.iterator();
            while(it.hasNext()){
                bookCopyList.add(conversionService.convert(it.next(), BookCopyDto.class));
            }
            return ResponseEntity.ok(bookCopyList);
        }
    }

    @GetMapping("bookcopy/{id}")
    public ResponseEntity<BookCopyDto> getOne(@PathVariable("id") Long id) {
        BookCopy bookCopy = bookCopyService.getOne(id);
        BookCopyDto bookCopyDto = conversionService.convert(bookCopy, BookCopyDto.class);
        return ResponseEntity.ok(bookCopyDto);
    }

    @GetMapping("bookcopies/{id}")
    public ResponseEntity<List<BookCopyDto>> getByBook(@PathVariable("id") Long id) {
        Collection<BookCopy> bookCopies = bookCopyService.getByBook(id);
        List<BookCopyDto> bookCopyList = new ArrayList<>();
        if(CollectionUtils.isEmpty(bookCopies)){
            return ResponseEntity.noContent().build();

        }else{
            Iterator<BookCopy> it = bookCopies.iterator();
            while(it.hasNext()){
                bookCopyList.add(conversionService.convert(it.next(), BookCopyDto.class));
            }
            return ResponseEntity.ok(bookCopyList);
        }
    }

    @DeleteMapping(value = "bookcopy/{id}")
    public ResponseEntity<BookCopyDto> delete(@PathVariable ("id") Long id){

        if(bookCopyService.existById(id)){
            BookCopy bookCopy = bookCopyService.getOne(id);
            BookCopyDto bookCopyDto = conversionService.convert(bookCopy, BookCopyDto.class);
            bookCopyService.delete(id);
            return ResponseEntity.ok(bookCopyDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "bookcopy")
    public ResponseEntity<BookCopyDto> create (@RequestBody BookCopyDto bookCopyDto){

            BookCopy bookCopy = conversionService.convert(bookCopyDto, BookCopy.class);
            bookCopyService.create(bookCopy);
            return ResponseEntity.ok(bookCopyDto);

    }
    @PutMapping(value = "bookcopy")
    public ResponseEntity<BookCopyDto> update (@RequestBody BookCopyDto bookCopyDto){
        BookCopy bookCopy = conversionService.convert(bookCopyDto, BookCopy.class);
        if(bookCopyService.existById(bookCopy.getId())){
            bookCopyService.update(bookCopy);
            return ResponseEntity.ok(bookCopyDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
