package com.sensor.SensorServer.repositories;

import com.sensor.SensorServer.models.SensorUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<SensorUser, Integer> {
    Optional<SensorUser> findByUsername(String username);
}
