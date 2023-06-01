package com.sensor.SensorServer.utils;

import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;

public class SensorUserException extends RuntimeException{
    private final HttpStatus httpStatus;

    public SensorUserException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
