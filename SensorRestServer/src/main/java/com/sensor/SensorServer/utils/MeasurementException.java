package com.sensor.SensorServer.utils;

import org.springframework.http.HttpStatus;

public class MeasurementException extends RuntimeException{
    private final HttpStatus httpStatus;

    public MeasurementException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
