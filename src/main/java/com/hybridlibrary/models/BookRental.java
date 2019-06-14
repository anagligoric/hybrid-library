package com.hybridlibrary.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BookRental extends AbstractModel implements Serializable {

    @ManyToOne
    @JoinColumn
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Book book;

    @NotNull
    private LocalDate returnDate;

    @Builder
    public BookRental(Long id, BookCopy bookCopy, User user, Book book, LocalDate returnDate){
        super(id);
        this.bookCopy = bookCopy;
        this.user = user;
        this.book = book;
        this.returnDate = returnDate;
    }
}
