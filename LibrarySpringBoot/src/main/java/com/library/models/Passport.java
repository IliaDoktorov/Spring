package com.library.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "number")
    private int number;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Passport() {
    }

    public Passport(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "id=" + id +
                ", number=" + number +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id == passport.id && number == passport.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }
}
