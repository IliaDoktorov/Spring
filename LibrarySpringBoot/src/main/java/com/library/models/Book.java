package com.library.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Book title cannot be empty.")
    @Size(min = 2, max = 50, message = "Book title should have 2-50 symbols.")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Author cannot be empty.")
    @Size(min = 2, max = 50, message = "Author should have 2-50 symbols.")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Initials should be separated by space and starts with capital letter.")
    private String author;

    @Column(name = "release_year")
    @Min(value = 1900, message = "Release year cannot be less than 1900.")
//    @Pattern(regexp = "\\d{4}", message = "Release year should have 4 digits.")
    private int releaseYear;

    @Column(name = "reserved_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservedAt;

    @Transient
    private boolean isOverdue = false;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book(String title, String author, int releaseYear) {
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
    }

    public Book() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(Date reservedAt) {
        this.reservedAt = reservedAt;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public void checkOverdue() {
        if(reservedAt != null) {
            long days = Duration.between(reservedAt.toInstant(), new Date().toInstant()).toDays();
            isOverdue = days > 10;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", releaseYear=" + releaseYear +
                ", reservedAt=" + reservedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && releaseYear == book.releaseYear && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, releaseYear);
    }
}
