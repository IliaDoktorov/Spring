package com.sensor.SensorServer.services;

import com.sensor.SensorServer.models.Measurement;
import com.sensor.SensorServer.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public void add(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        measurementRepository.save(measurement);
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    public long rainyDaysCount() {
        return measurementRepository.countByRaining(true);
    }
}
