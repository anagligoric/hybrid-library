package com.hybridlibrary.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String language;
    private Integer rentPeriod;

}
