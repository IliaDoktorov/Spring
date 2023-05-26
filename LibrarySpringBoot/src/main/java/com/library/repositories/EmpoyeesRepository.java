package com.library.repositories;

import com.library.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpoyeesRepository extends JpaRepository<Employee, Integer> {
    boolean existsByUsername(String username);
    Optional<Employee> findByUsername(String username);
}
