package com.library.services;

import com.library.models.Book;
import com.library.models.Person;
import com.library.repositories.PeopleRepository;
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
        return peopleRepository.findAll();
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
    }

    @Transactional
    public List<Person> findPerson(Person personToSearch){
        return peopleRepository.findByInitialsContainingIgnoreCaseOrYearOfBirth(personToSearch.getInitials(), personToSearch.getYearOfBirth());
    }
}
