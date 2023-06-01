package com.sensor.SensorServer.repositories;

import com.sensor.SensorServer.models.SensorUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<SensorUser, Integer> {
}
