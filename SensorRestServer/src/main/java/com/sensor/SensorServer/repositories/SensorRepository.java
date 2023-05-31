package com.sensor.SensorServer.repositories;

import com.sensor.SensorServer.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, String> {
    boolean existsByName(String name);
}
