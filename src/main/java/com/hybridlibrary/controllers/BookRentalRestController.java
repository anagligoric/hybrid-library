package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.services.BookRentalService;
import com.hybridlibrary.services.serviceimpl.BookRentalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalService bookRentalService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("bookrental")
    public ResponseEntity<List<BookRentalDto>> findAll(){
        Collection<BookRental> bookRentals = bookRentalService.findAll();
        List<BookRentalDto> bookRentalList = new ArrayList<>();
        if(CollectionUtils.isEmpty(bookRentals)){
            return ResponseEntity.noContent().build();
        }else{
            Iterator<BookRental> it = bookRentals.iterator();
            while(it.hasNext()){
                bookRentalList.add(conversionService.convert(it.next(), BookRentalDto.class));
            }
            return ResponseEntity.ok(bookRentalList);
        }
    }

    @GetMapping("bookrental/{id}")
    public ResponseEntity<BookRentalDto> getOne(@PathVariable("id") Long id){

        BookRentalDto bookRentalDto = conversionService.convert(bookRentalService.getOne(id), BookRentalDto.class);

        return ResponseEntity.ok(bookRentalDto);
    }

    @DeleteMapping("bookrental/{id}")
    public ResponseEntity<BookRentalDto> delete(@PathVariable("id") Long id){
        if(bookRentalService.existById(id)){
            BookRentalDto bookRentalDto = conversionService.convert(bookRentalService.getOne(id), BookRentalDto.class);
            bookRentalService.delete(id);
            return ResponseEntity.ok(bookRentalDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping("bookrental")
    public ResponseEntity<BookRentalDto> create(@RequestBody BookRentalDto bookRentalDto){

            BookRental bookRental = conversionService.convert(bookRentalDto, BookRental.class);
            bookRentalService.create(bookRental);
            return ResponseEntity.ok(bookRentalDto);

    }
    @PutMapping("bookrental")
    public ResponseEntity<BookRentalDto> update(@RequestBody BookRentalDto bookRentalDto){
        BookRental bookRental = conversionService.convert(bookRentalDto, BookRental.class);
        if(bookRentalService.existById(bookRental.getId())){
            bookRentalService.update(bookRental);
            return ResponseEntity.ok(bookRentalDto);
        }else{
            return ResponseEntity.noContent().build();
        }
    }




}
