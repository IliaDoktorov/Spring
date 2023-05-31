package com.sensor.SensorServer.utils;

import org.springframework.http.HttpStatus;

public class SensorException extends RuntimeException{
    private final HttpStatus httpStatus;
    public SensorException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
