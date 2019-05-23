package com.hybridlibrary.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bookCopy")
@Data
public class BookCopy implements Serializable {
    @Id
    @SequenceGenerator(name="BOOK_COPY_ID_GENERATOR", sequenceName = "BOOK_COPY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOOK_COPY_ID_GENERATOR")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="book")
    private Book book;
}
