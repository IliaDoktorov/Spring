package com.library.controllers;

import com.library.models.Employee;
import com.library.services.EmployeeDetailsService;
import com.library.services.RegistrationService;
import com.library.util.EmployeeValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/login")
    public String loginPage(){
        return "authentication/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("employee")Employee employee){
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String newEmployee(@ModelAttribute("employee") @Valid Employee employee,
                              BindingResult bindingResult){

        employeeValidator.validate(employee, bindingResult);

        if(bindingResult.hasErrors())
            return "authentication/registration";

        registrationService.createNewEmployee(employee);

        return "redirect:/login?successful_registration";
    }
}
