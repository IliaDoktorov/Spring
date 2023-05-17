package org.library.util;

import org.library.models.Person;
import org.library.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonSearchValidator implements Validator {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonSearchValidator(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(person.getInitials().isEmpty())
            errors.rejectValue("initials","", "Initials cannot be empty!");

        if(person.getYearOfBirth() < Person.YEAROFBIRTH_LOWERBOUND)
            errors.rejectValue("yearOfBirth", "", "Year of birth cannot be less than 1900");
    }
}
