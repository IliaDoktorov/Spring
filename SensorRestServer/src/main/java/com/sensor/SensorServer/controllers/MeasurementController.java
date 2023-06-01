package com.sensor.SensorServer.controllers;

import com.sensor.SensorServer.dto.MeasurementDTO;
import com.sensor.SensorServer.models.Measurement;
import com.sensor.SensorServer.services.MeasurementService;
import com.sensor.SensorServer.utils.ErrorResponse;
import com.sensor.SensorServer.utils.MeasurementException;
import com.sensor.SensorServer.utils.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;

    private final MeasurementValidator measurementValidator;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> findAll(){
        List<MeasurementDTO> measurementDTOList = measurementService.findAll()
                .stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(measurementDTOList, HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public long rainyDaysCount(){
        return measurementService.rainyDaysCount();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {

        measurementValidator.validate(measurementDTO, bindingResult);

        measurementService.add(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(MeasurementException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
