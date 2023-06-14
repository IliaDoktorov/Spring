package com.HRM.repositories;

import com.HRM.models.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<Unit, Integer> {
    Optional<Unit> findByName(String parentUnit);

    Optional<Unit> existsByName(String s);
}
