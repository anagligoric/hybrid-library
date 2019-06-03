package com.hybridlibrary.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy extends AbstractModel implements Serializable {

    @NotNull
    @ManyToOne
    @JoinColumn
    private Book book;

    @Column
    @NotNull(message = "Rent status can not be null")
    private Boolean rented;

    @Column
    private LocalDate rentDate;

    @ManyToOne
    @JoinColumn
    private User user;

    @Builder
    public BookCopy(Long id, Book book, Boolean rented, LocalDate rentDate, User user){
        super(id);
        this.book = book;
        this.rentDate = rentDate;
        this.rented = rented;
        this.user = user;
    }


}
