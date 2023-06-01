package com.sensor.SensorServer.controllers;

import com.sensor.SensorServer.models.Sensor;
import com.sensor.SensorServer.services.SensorService;
import com.sensor.SensorServer.utils.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;

    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<Sensor> allSensors(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "items_per_page", required = false, defaultValue = "10") int itemsPerPage,
                                   @RequestParam(value = "sort", required = false, defaultValue = "name") String sortBy){
        return sensorService.findAll(page, itemsPerPage, sortBy);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid Sensor sensor,
                                                     BindingResult bindingResult) {

        sensorValidator.validate(sensor, bindingResult);

        sensorService.registerSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/unregister")
    public ResponseEntity<HttpStatus> unregisterSensor(@RequestBody @Valid Sensor sensor,
                                                       BindingResult bindingResult) {
        sensorValidator.validate(sensor, bindingResult);

        sensorService.unregisterSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
