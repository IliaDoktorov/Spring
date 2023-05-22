package com.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
//@NamedEntityGraph(name = "person-books-graph",
//        attributeNodes = @NamedAttributeNode(value = "books"))
public class Person {
    public static final int YEAROFBIRTH_LOWERBOUND = 1901;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "initials")
    @NotEmpty(message = "Initials cannot be empty.")
    @Size(min = 3, max = 50, message = "Initials should have 2-30 symbols.")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Initials should be separated by space and starts with capital letter.")
    private String initials;

    @Column(name = "year_of_birth")
//    @Min(value = YEAROFBIRTH_LOWERBOUND, message = "Year of birth cannot be less than 1900.")
//    @Pattern(regexp = "\\d{4}", message = "Date if birth should have 4 digits.")
//    @NotEmpty(message = "Year of birth cannot be empty")
//    @NotBlank(message = "Year of birth cannot be blank")
    private int yearOfBirth;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Passport passport;

    public Person(String initials, int yearOfBirth) {
        this.initials = initials;
        this.yearOfBirth = yearOfBirth;
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", initials='" + initials + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", passport=" + passport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && yearOfBirth == person.yearOfBirth && Objects.equals(initials, person.initials) && Objects.equals(passport, person.passport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, initials, yearOfBirth, passport);
    }
}
