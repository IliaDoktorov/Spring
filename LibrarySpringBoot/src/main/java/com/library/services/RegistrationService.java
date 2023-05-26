package com.library.services;

import com.library.models.Employee;
import com.library.repositories.EmpoyeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {
    private final EmpoyeesRepository empoyeesRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(EmpoyeesRepository empoyeesRepository, PasswordEncoder passwordEncoder) {
        this.empoyeesRepository = empoyeesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existByUsername(String username){
        return empoyeesRepository.existsByUsername(username);
    }

    public void register(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        empoyeesRepository.save(employee);
    }
}
