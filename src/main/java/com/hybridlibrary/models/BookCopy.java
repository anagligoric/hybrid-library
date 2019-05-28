package com.hybridlibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
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
    @Temporal(TemporalType.DATE)
    private Date rentDate;

    @ManyToOne
    @JoinColumn
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "bookCopy")
    private List<BookRental> bookRentals;



}
