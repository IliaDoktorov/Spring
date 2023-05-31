package com.sensor.SensorServer.services;

import com.sensor.SensorServer.models.Sensor;
import com.sensor.SensorServer.repositories.SensorRepository;
import com.sensor.SensorServer.utils.SensorException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void registerSensor(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public void unregisterSensor(Sensor sensor) {
        if(!sensorRepository.existsByName(sensor.getName()))
            throw new SensorException("Sensor with such name doesn't exist", HttpStatus.NOT_FOUND);

        sensorRepository.delete(sensor);
    }

    public List<Sensor> findAll(int page, int itemsPerPage, String sortBy) {
        return sensorRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by(sortBy))).getContent();
    }
}
