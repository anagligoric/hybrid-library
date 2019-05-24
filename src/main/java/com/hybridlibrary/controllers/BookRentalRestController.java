package com.hybridlibrary.controllers;

import com.hybridlibrary.services.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalService bookRentalService;

    /*@GetMapping("book")
    public ResponseEntity<HashMap<String, Integer>> mostRentedBooks(){
        return ResponseEntity.ok(bookRentalService.mostRentedBook());
    }*/
}
