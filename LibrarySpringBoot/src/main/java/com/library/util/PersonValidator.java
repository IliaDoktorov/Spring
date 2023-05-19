package com.library.util;

import com.library.models.Person;
import com.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PersonValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(peopleRepository.findByInitials(person.getInitials()).isPresent()){
            errors.rejectValue("initials", "", "Person with these Initials already exist!");
        }

        if(person.getYearOfBirth() < Person.YEAROFBIRTH_LOWERBOUND){
            errors.rejectValue("yearOfBirth", "", "Year of birth cannot be less than 1900!");
        }
    }
}
