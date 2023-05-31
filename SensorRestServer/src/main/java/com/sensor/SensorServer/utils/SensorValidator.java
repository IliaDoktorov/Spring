package com.sensor.SensorServer.utils;

import com.sensor.SensorServer.models.Sensor;
import com.sensor.SensorServer.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SensorValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        // check for fields errors, for example name
        if (errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> allErrors = errors.getFieldErrors();
            allErrors.forEach(error -> errorMsg
                            .append(error.getDefaultMessage())
                            .append(";")
            );
            throw new SensorException(errorMsg.toString(), HttpStatus.BAD_REQUEST);
        }

        if (sensorRepository.existsByName(sensor.getName()))
            throw new SensorException("Sensor with such name already exist", HttpStatus.CONFLICT);

    }
}
