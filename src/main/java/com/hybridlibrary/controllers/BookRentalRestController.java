package com.hybridlibrary.controllers;

import com.hybridlibrary.repositories.BookRentCount;
import com.hybridlibrary.services.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("{id}")
@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalService bookRentalService;

    @GetMapping("/mostrented")
    public ResponseEntity<List<BookRentCount>> getMostRented(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookRentalService.mostRented(id));
    }

}
