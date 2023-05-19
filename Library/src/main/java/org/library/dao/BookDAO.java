package org.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.library.models.Book;
import org.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BookDAO {

//    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
//        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> index() {
//        return jdbcTemplate.query("SELECT * FROM BOOK", new BookMapper(true));
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }

    @Transactional
    public Book getBookById(int id) {
        Session session = sessionFactory.getCurrentSession();
        // don't need to get owner, since fetch = FetchType.EAGER by default on Book
        return session.get(Book.class, id);
    }

    @Transactional
    public void createNewBook(Book book) {
//        jdbcTemplate.update("INSERT INTO BOOK(author, release_year, title) VALUES(?, ?, ?)", book.getAuthor(), book.getReleaseYear(), book.getTitle());
        Session session = sessionFactory.getCurrentSession();
        session.persist(book);
    }

    @Transactional
    public void updateBook(Book book, int id) {
//        jdbcTemplate.update("UPDATE BOOK SET author=?, release_year=?, title=? WHERE id=?", book.getAuthor(), book.getReleaseYear(), book.getTitle(), id);
        Session session = sessionFactory.getCurrentSession();
        book.setId(id);
        session.merge(book);
    }

    @Transactional
    public void deleteBookById(int id) {
//        jdbcTemplate.update("DELETE FROM BOOK WHERE id=?", id);
        Session session = sessionFactory.getCurrentSession();
        Book book = new Book();
        book.setId(id);
        session.remove(book);
    }

    @Transactional
    public void vacate(int id) {
//        jdbcTemplate.update("UPDATE BOOK SET person_id=? WHERE id=?", null, id);
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void reserveBook(int bookId, int personId) {
//        jdbcTemplate.update("UPDATE BOOK SET person_id=? WHERE id=?", personId, bookId);
        Session session = sessionFactory.getCurrentSession();

//        Person personProxy = session.load(Person.class, personId);
        Person personProxy = session.getReference(Person.class, personId);

        Book book = session.get(Book.class, bookId);

        book.setOwner(personProxy);
    }
}
