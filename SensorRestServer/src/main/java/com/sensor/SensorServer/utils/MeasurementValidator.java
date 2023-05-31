package com.sensor.SensorServer.utils;

import com.sensor.SensorServer.dto.MeasurementDTO;
import com.sensor.SensorServer.models.Measurement;
import com.sensor.SensorServer.repositories.MeasurementRepository;
import com.sensor.SensorServer.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class MeasurementValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurementDTO = (MeasurementDTO) target;

        if(measurementDTO.getValue() < -60.0 || measurementDTO.getValue() > 60.0)
            errors.rejectValue("value", "", "Value cannot be less than -60.0 and greater than +60.0");

        if (errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> allErrors = errors.getFieldErrors();
            allErrors.forEach(error -> errorMsg
                    .append(error.getDefaultMessage())
                    .append(";")
            );
            throw new MeasurementException(errorMsg.toString(), HttpStatus.BAD_REQUEST);
        }

        if(!sensorRepository.existsByName(measurementDTO.getSensor().getName()))
            throw new MeasurementException("Sensor with such name doesn't exist", HttpStatus.NOT_FOUND);
    }
}
