package com.hybridlibrary.dtos;

import lombok.Data;

@Data
public class BookDto {

   private String title;
   private String author;
   private String language;
   private Integer rentPeriod;

}
