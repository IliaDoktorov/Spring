package org.library.services;

import org.hibernate.Hibernate;
import org.library.models.Book;
import org.library.models.Person;
import org.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(boolean fetchBooks){
        List<Person> people = peopleRepository.findAll();
        if(fetchBooks)
            people.forEach(person -> Hibernate.initialize(person.getBooks()));
        return people;
    }

    public Person findById(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null) {
            person.getBooks().forEach(Book::checkOverdue);
        }
        return person;
    }

    @Transactional(readOnly = false)
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void update(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void deleteById(int id){
        peopleRepository.deleteById(id);
//        peopleRepository.delete(person);
    }

    @Transactional
    public List<Person> findPerson(Person personToSearch){
        List<Person> people = peopleRepository.findByInitialsContainingIgnoreCaseOrYearOfBirth(personToSearch.getInitials(), personToSearch.getYearOfBirth());
        people.forEach(person -> Hibernate.initialize(person.getBooks()));
        return people;
    }
}
