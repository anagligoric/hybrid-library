package com.hybridlibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String username;


    @Column
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy="bookCopy")
    private List<BookRental> bookRentals;

}
