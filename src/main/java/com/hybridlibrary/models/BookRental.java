package com.hybridlibrary.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class BookRental extends AbstractModel implements Serializable {

    @ManyToOne
    @JoinColumn
    private BookCopy bookCopy;

    @FutureOrPresent(message = "Rent date can not be in the past")
    @Temporal(TemporalType.DATE)
    private Date rentDate;

    @FutureOrPresent(message = "Return date can not be in the past")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
}
