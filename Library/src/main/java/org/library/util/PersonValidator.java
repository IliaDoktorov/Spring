package org.library.util;

import org.library.dao.PersonDAO;
import org.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;
    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(personDAO.validateInitials(person.getInitials()).isPresent()){
            errors.rejectValue("initials", "", "Person with these Initials already exist!");
        }

        if(person.getYearOfBirth() < Person.YEAROFBIRTH_LOWERBOUND){
            errors.rejectValue("yearOfBirth", "", "Year of birth cannot be less than 1900!");
        }
    }
}
