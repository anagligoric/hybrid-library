package com.hybridlibrary.repositories;

public interface BookRentCount {

    Long getBookId();

    String getBookTitle();

    String getBookAuthor();

    String getBookLanguage();

    Long getRentalCount();
}
