package com.hybridlibrary.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends AbstractModel implements Serializable {


    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String language;

    @Column
    private Integer rentPeriod;

    @JsonIgnore
    @OneToMany(mappedBy="book")
    private List<BookCopy> bookCopies;
}
