package com.sensor.SensorServer.controllers;

import com.sensor.SensorServer.dto.SensorUserDTO;
import com.sensor.SensorServer.models.SensorUser;
import com.sensor.SensorServer.security.AuthProviderImpl;
import com.sensor.SensorServer.security.JWTUtil;
import com.sensor.SensorServer.services.SensorUserDetailsService;
import com.sensor.SensorServer.utils.ErrorResponse;
import com.sensor.SensorServer.utils.SensorUserException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthController {
    private final SensorUserDetailsService sensorUserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(SensorUserDetailsService sensorUserDetailsService, AuthenticationManager authenticationManager, JWTUtil jwtUtil, ModelMapper modelMapper) {
        this.sensorUserDetailsService = sensorUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody @Valid SensorUserDTO sensorUserDTO,
                                                            BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new SensorUserException(HttpStatus.BAD_REQUEST, bindingResult.getFieldError("username").getDefaultMessage());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                sensorUserDTO.getUsername(),
                sensorUserDTO.getPassword()
        );

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException ex){
            throw new SensorUserException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        String token = jwtUtil.generateToken(sensorUserDTO.getUsername());
        return new ResponseEntity<>(Map.of("jwt-token", token), HttpStatus.OK);
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
