package com.hybridlibrary.controllers;

import com.hybridlibrary.services.BookRentalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BookRentalRestController {

    @Autowired
    private BookRentalService bookRentalService;


}
