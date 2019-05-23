package com.hybridlibrary.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class BookRental extends AbstractModel implements Serializable {

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private BookCopy bookCopy;

    @Temporal(TemporalType.DATE)
    private Date rentDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;
}
