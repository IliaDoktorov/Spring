package org.library.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
//    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
//        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Person", Person.class).getResultList();
//        return jdbcTemplate.query("SELECT * FROM PERSON", new PersonMapper());
    }

    @Transactional
    public Person getPersonById(int id) {
//        return (Person) jdbcTemplate.query("SELECT * FROM PERSON WHERE person_id=?", new Object[]{id}, new PersonMapper())
//                .stream()
//                .findAny()
//                .orElse(null);
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        // fill books due to fetch = FetchType.LAZY on Person
        Hibernate.initialize(person.getBooks());
        return person;
    }

    @Transactional
    public void createNewPerson(Person person) {
//        jdbcTemplate.update("INSERT INTO PERSON(initials, year_of_birth) VALUES(?, ?)", person.getInitials(), person.getYearOfBirth());
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
    }

    @Transactional
    public Optional<Person> validateInitials(String initials){
//        return jdbcTemplate.query("SELECT * FROM PERSON WHERE initials=?", new Object[]{initials}, new PersonMapper())
//                .stream().findAny();
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p where p.initials=:initials", Person.class)
                .setParameter("initials", initials)
                .getResultList()
                .stream().findAny();
    }

    @Transactional
    public void updatePerson(Person persontoUpdate, int id) {
        Session session = sessionFactory.getCurrentSession();
        persontoUpdate.setId(id);
        session.merge(persontoUpdate);
//        jdbcTemplate.update("UPDATE PERSON SET initials=?, year_of_birth=? WHERE person_id=?", person.getInitials(), person.getYearOfBirth(), id);
    }

    @Transactional
    public void deletePersonById(int id) {
//        jdbcTemplate.update("DELETE FROM PERSON WHERE person_id=?", id);
        Session session = sessionFactory.getCurrentSession();
        Person personToRemove = new Person();
        personToRemove.setId(id);
        session.remove(personToRemove);
    }

//    public List<Book> booksByPerson(int id) {
//        return jdbcTemplate.query("SELECT author, release_year, title FROM PERSON JOIN BOOK ON PERSON.person_id=BOOK.person_id WHERE PERSON.person_id=?", new Object[]{id}, new BookMapper(false));
//    }
}
