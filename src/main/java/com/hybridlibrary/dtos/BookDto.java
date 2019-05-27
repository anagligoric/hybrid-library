package com.hybridlibrary.dtos;

import lombok.Data;

@Data
public class BookDto {

    String title;
    String author;
    String language;
    Integer rentPeriod;

}
