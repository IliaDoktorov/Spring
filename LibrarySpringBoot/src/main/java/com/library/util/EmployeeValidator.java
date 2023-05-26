package com.library.util;

import com.library.models.Employee;
import com.library.services.EmployeeDetailsService;
import com.library.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EmployeeValidator implements Validator {
    private final RegistrationService registrationService;

    @Autowired
    public EmployeeValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;

        if(registrationService.existByUsername(employee.getUsername()))
            errors.rejectValue("username", "", "Employee with such username already exist");

    }
}
