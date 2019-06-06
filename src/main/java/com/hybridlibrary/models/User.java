package com.hybridlibrary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column
    @NotBlank(message = "First name can not be blank")
    private String firstName;

    @Column
    @NotBlank(message = "Last name can not be blank")
    private String lastName;

    @Column
    @Email(message = "Email should be valid")
    private String email;

    @Column
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Column
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Column
    @NotNull
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<BookCopy> bookCopies;

    @Builder
    public User(Long id, String firstName, String lastName, String email, String username, String password, String role) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
