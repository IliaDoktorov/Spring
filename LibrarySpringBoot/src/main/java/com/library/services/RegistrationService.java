package com.library.services;

import com.library.models.Employee;
import com.library.repositories.EmpoyeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegistrationService {
    private final EmpoyeesRepository empoyeesRepository;

    @Autowired
    public RegistrationService(EmpoyeesRepository empoyeesRepository) {
        this.empoyeesRepository = empoyeesRepository;
    }

    public boolean existByUsername(String username){
        return empoyeesRepository.existsByUsername(username);
    }

    public void createNewEmployee(Employee employee) {
        empoyeesRepository.save(employee);
    }
}
