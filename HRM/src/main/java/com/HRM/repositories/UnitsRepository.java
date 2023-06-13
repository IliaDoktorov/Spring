package com.HRM.repositories;

import com.HRM.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitsRepository extends JpaRepository<Unit, Integer> {
    Optional<Unit> findByName(String parentUnit);
}
