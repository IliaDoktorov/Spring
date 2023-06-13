package com.HRM.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty
    @Column(name = "last_name")
    private String lastName;
    @Email
    @Column(name = "email")
    private String email;
    @Column(name = "is_active")
    private boolean isActive; // represents working\fired functionality
    @ManyToOne
    @JoinColumn(name = "position", referencedColumnName = "id")
    private Position position;
    @ManyToMany(mappedBy = "people")
    private List<Unit> units;

}
