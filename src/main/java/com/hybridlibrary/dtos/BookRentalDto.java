package com.hybridlibrary.dtos;

import com.hybridlibrary.models.BookCopy;
import com.hybridlibrary.models.User;
import lombok.Data;

import java.util.Date;

@Data
public class BookRentalDto {
    private User user;
    private BookCopy bookCopy;
    private Date rentDate;
    private Date returnDate;
}
