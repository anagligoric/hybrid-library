package com.hybridlibrary.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "book")
@Data
//@JsonIgnoreProperties({"hibernateLazyInitalizer", "handler"})
public class Book implements Serializable {
    @Id
    @SequenceGenerator(name="BOOK_ID_GENERATOR", sequenceName = "BOOK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOOK_ID_GENERATOR")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column (name = "language")
    private String language;

    @Column (name = "rentPeriod")
    private int rentPeriod;

    @JsonIgnore
    @OneToMany(mappedBy="book")
    private List<BookCopy> bookCopies;
}
