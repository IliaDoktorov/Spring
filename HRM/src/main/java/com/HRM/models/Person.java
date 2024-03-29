package com.HRM.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;
import java.util.Set;

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
    @JsonProperty
    @Column(name = "is_active")
    private boolean isActive; // represents working\fired functionality
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "position", referencedColumnName = "id")
    private Position position;

    // Set insteadof List because List will cause removing all records from join table before insert new
    // It happens because List could contain duplicated values
    //
    // CascadeType.PERSIST, CascadeType.MERGE only. In case of CascadeType.ALL
    // all records from unit_person table will be removed, if we remove some person
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "people", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JsonBackReference
    private Set<Unit> units;

    public Person() {
    }

    public Person(String firstName, String lastName, String email, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }
}
