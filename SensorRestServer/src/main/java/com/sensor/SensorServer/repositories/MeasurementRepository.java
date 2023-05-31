package com.sensor.SensorServer.repositories;

import com.sensor.SensorServer.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    long countByRaining(boolean raining);
}
