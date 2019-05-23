package com.hybridlibrary.controllers;

import com.hybridlibrary.services.BookRentalService;
import com.hybridlibrary.services.serviceImpl.BookRentalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalServiceImpl bookRentalService;

    /*@GetMapping("book")
    public ResponseEntity<HashMap<String, Integer>> mostRentedBooks(){
        return ResponseEntity.ok(bookRentalService.mostRentedBook());
    }*/
}
