package com.hybridlibrary.models;

import javax.persistence.*;

public class BookRental {

    @Id
    @SequenceGenerator(name="BOOK_ID_GENERATOR", sequenceName = "BOOK_SEQ")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="BOOK_ID_GENERATOR")
    private Integer id;





}
