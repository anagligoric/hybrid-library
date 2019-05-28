package com.hybridlibrary.dtos;

import com.hybridlibrary.models.Book;
import com.hybridlibrary.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookCopyDto {
    private Long bookId;
    private boolean rented;
    private Date rentDate;
    private User user;
}
