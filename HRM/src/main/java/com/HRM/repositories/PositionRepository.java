package com.HRM.repositories;

import com.HRM.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    Optional<Position> existsByName(String position);

    Optional<Position> findByName(String position);
}