package com.sensor.SensorServer.controllers;

import com.sensor.SensorServer.dto.SensorUserDTO;
import com.sensor.SensorServer.models.SensorUser;
import com.sensor.SensorServer.security.JWTUtil;
import com.sensor.SensorServer.utils.ErrorResponse;
import com.sensor.SensorServer.utils.SensorUserException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(JWTUtil jwtUtil, ModelMapper modelMapper) {
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public Map<String, String> authenticate(@RequestBody @Valid SensorUserDTO sensorUserDTO,
                                                            BindingResult bindingResult){
        SensorUser sensorUser = convertToSensorUser(sensorUserDTO);

        if(bindingResult.hasErrors())
            throw new SensorUserException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError("username").getDefaultMessage());

        // register user


        String token = jwtUtil.generateToken(sensorUser.getUsername());
        return Map.of("jwt-token", token);
    }

    private SensorUser convertToSensorUser(SensorUserDTO sensorUserDTO){
        return modelMapper.map(sensorUserDTO, SensorUser.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> exceptionHandler(SensorUserException ex){
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
