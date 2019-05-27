package com.hybridlibrary.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends AbstractModel implements Serializable {


    @Column
    @NotBlank(message = "Book title can not be blank")
    private String title;

    @Column
    @NotBlank(message = "Book author can not be blank")
    private String author;

    @Column
    @NotBlank(message = "Book language can not be blank")
    private String language;

    @Column
    @NotNull(message = "Book rent period can not be null")
    private Integer rentPeriod;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<BookCopy> bookCopies;
}
