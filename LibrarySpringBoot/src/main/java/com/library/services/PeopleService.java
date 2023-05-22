package com.library.services;

import com.library.models.Book;
import com.library.models.Passport;
import com.library.models.Person;
import com.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
        Passport passport = new Passport(person.getPassport().getNumber());

        passport.setOwner(person);
        person.setPassport(passport);

        peopleRepository.save(person);
    }

    @Transactional(readOnly = false)
    public void update(int id, Person personToUpdate){
        // implementation depends on data from HTML page
        // if we get only updated passport number we need to refresh our data
        // since passport from HTML has id=0 and Hibernate will insert new record for passport instead of updating existing
        //
        // but if we get passport object from HTML, we will get valid id for passport as well
        // so no need to refresh the data
        personToUpdate.setId(id);

        Passport passportToUpdate = peopleRepository.findById(id).orElse(null).getPassport();

        passportToUpdate.setNumber(personToUpdate.getPassport().getNumber());

        personToUpdate.setPassport(passportToUpdate);

        peopleRepository.save(personToUpdate);
    }

    @Transactional(readOnly = false)
    public void deleteById(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public List<Person> findByQuery(String query){
        Person person;
        if(query.chars().allMatch(Character::isDigit)) {
            person = new Person(query, Integer.parseInt(query));
            person.setPassport(new Passport(query));
        }
        else
            person = new Person(query, 0);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("initials", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("yearOfBirth", ExampleMatcher.GenericPropertyMatchers.exact())
                .withMatcher("passport.number", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Person> personExample = Example.of(person, matcher);

        return peopleRepository.findAll(personExample);
    }
}
