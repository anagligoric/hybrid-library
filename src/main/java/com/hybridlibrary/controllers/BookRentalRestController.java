package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookRentalDto;
import com.hybridlibrary.models.BookRental;
import com.hybridlibrary.services.BookRentalService;
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

@Slf4j
@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalService bookRentalService;

    @Autowired
    private ConversionService conversionService;

    @GetMapping("bookrental")
    public ResponseEntity<List<BookRentalDto>> findAll() {
        Collection<BookRental> bookRentals = bookRentalService.findAll();
        List<BookRentalDto> bookRentalList = new ArrayList<>();
        if (CollectionUtils.isEmpty(bookRentals)) {
            return ResponseEntity.noContent().build();
        } else {
            Iterator<BookRental> it = bookRentals.iterator();
            while (it.hasNext()) {
                bookRentalList.add(conversionService.convert(it.next(), BookRentalDto.class));
            }
            log.info("Book rentals fetched");

            return ResponseEntity.ok(bookRentalList);
        }
    }

    @GetMapping("bookrental/{id}")
    public ResponseEntity<BookRentalDto> getOne(@PathVariable("id") Long id) {
        if (bookRentalService.existById(id)) {
            BookRentalDto bookRentalDto = conversionService.convert(bookRentalService.getOne(id), BookRentalDto.class);
            log.info("Book rental with id {} is listed", id);
            return ResponseEntity.ok(bookRentalDto);
        } else {
            return ResponseEntity.noContent().build();
        }


    }

    @DeleteMapping("bookrental/{id}")
    public ResponseEntity<BookRentalDto> delete(@PathVariable("id") Long id) {
        if (bookRentalService.existById(id)) {
            BookRentalDto bookRentalDto = conversionService.convert(bookRentalService.getOne(id), BookRentalDto.class);
            bookRentalService.delete(id);
            log.info("Book rental with id {} is deleted", id);
            return ResponseEntity.ok(bookRentalDto);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("bookrental")
    public ResponseEntity<BookRentalDto> create(@RequestBody BookRentalDto bookRentalDto) {
        try {
            BookRental bookRental = conversionService.convert(bookRentalDto, BookRental.class);
            bookRentalService.create(bookRental);
            return ResponseEntity.ok(bookRentalDto);
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }


    }

    @PutMapping("bookrental")
    public ResponseEntity<BookRentalDto> update(@RequestBody BookRental bookRental) {
        try {
            if (bookRentalService.existById(bookRental.getId())) {
                bookRentalService.update(bookRental);
                BookRentalDto bookRentalDto = conversionService.convert(bookRental, BookRentalDto.class);
                return ResponseEntity.ok(bookRentalDto);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (ConstraintViolationException e) {
            log.error("Error occurred {}", e.toString(), e);
            return ResponseEntity.badRequest().build();
        }

    }


}
