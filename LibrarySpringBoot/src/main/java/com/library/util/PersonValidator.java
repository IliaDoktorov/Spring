package com.library.util;

import com.library.models.Person;
import com.library.repositories.PassportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PassportRepository passportRepository;
    @Autowired
    public PersonValidator(PassportRepository passportRepository) {
        this.passportRepository = passportRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // since we have only yearOfBirth field from person and number filed from passport that require validation
        // decided to combine both validation here and not create separate validator for passport
        if(passportRepository.existsByNumber(person.getPassport().getNumber()))
            errors.rejectValue("passport.number", "", "Passport with such number already exist");

        if(person.getPassport().getNumber() == null || person.getPassport().getNumber().isEmpty() || person.getPassport().getNumber().isBlank())
            errors.rejectValue("passport.number", "", "Passport number cannot be blank or empty");

        if(!person.getPassport().getNumber().matches("\\d{4}\s\\d{6}"))
            errors.rejectValue("passport.number", "", "Passport should be in format XXXX YYYYYY and contain only digits");


        if(person.getYearOfBirth() < Person.YEAROFBIRTH_LOWERBOUND)
            errors.rejectValue("yearOfBirth", "", "Year of birth cannot be less than " + Person.YEAROFBIRTH_LOWERBOUND);
    }
}
