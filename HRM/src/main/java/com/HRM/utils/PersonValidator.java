package com.HRM.utils;

import com.HRM.dto.PersonDTO;
import com.HRM.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PersonDTO personDTO = (PersonDTO) target;

        if(errors.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorList = errors.getFieldErrors();
            errorList.forEach(error -> errorMsg
                    .append(error.getDefaultMessage())
                    .append(";")
            );

            throw new PersonValidationException(HttpStatus.BAD_REQUEST, errorMsg.toString());
        }
    }
}
