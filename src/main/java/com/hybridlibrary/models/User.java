package com.hybridlibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column
    @NotBlank
    private String firstName;

    @Column
    @NotBlank
    private String lastName;

    @Column
    @Email(message = "Email should be valid")
    private String email;

    @Column
    @NotBlank
    private String username;

    @Column
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BookCopy> bookCopies;



}
