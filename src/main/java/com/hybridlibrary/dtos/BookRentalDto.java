package com.hybridlibrary.dtos;

import com.hybridlibrary.models.BookCopy;
import lombok.Data;

import java.util.Date;

@Data
public class BookRentalDto {
    private UserDto user;
    private BookCopy bookCopy;
    private Date rentDate;
    private Date returnDate;
}
