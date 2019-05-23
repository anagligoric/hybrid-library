package com.hybridlibrary.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="USER_ID_GENERATOR", sequenceName="USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_ID_GENERATOR")
    private Integer id;

    @Column(name="username")
    private String username;


    @Column(name="password")
    private String password;



}
