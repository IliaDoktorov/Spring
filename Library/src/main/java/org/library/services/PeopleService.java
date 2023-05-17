package org.library.services;

import org.hibernate.Hibernate;
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

    public List<Person> findAll(){
        List<Person> people = peopleRepository.findAll();
        people.forEach(person -> Hibernate.initialize(person.getBooks()));
        return people;
    }

    public Person findById(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        if(person != null)
            Hibernate.initialize(person.getBooks());
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
    public List<Person> findPerson(Person person){
        return peopleRepository.findByInitialsContainingIgnoreCaseOrYearOfBirth(person.getInitials(), person.getYearOfBirth());
    }
}