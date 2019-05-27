package com.hybridlibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class BookCopy extends AbstractModel implements Serializable {

    @ManyToOne
    @JoinColumn
    private Book book;

    @JsonIgnore
    @OneToMany(mappedBy = "bookCopy")
    private List<BookRental> bookRentals;
}
