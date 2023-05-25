package com.library.services;

import com.library.models.Employee;
import com.library.repositories.EmpoyeesRepository;
import com.library.security.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeeDetailsService implements UserDetailsService {
    private EmpoyeesRepository empoyeesRepository;

    @Autowired
    public EmployeeDetailsService(EmpoyeesRepository empoyeesRepository) {
        this.empoyeesRepository = empoyeesRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = empoyeesRepository.findByUsername(username);

        if(employee.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new EmployeeDetails(employee.get());
    }
}
