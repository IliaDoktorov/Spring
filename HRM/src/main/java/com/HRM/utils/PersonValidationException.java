package com.HRM.utils;

import org.springframework.http.HttpStatus;

public class PersonValidationException  extends RuntimeException{
    private final HttpStatus httpStatus;

    public PersonValidationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
