package com.HRM.utils;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ResponseEntry {
    private String message;
    private long timestamp;

    public ResponseEntry() {
    }

    public ResponseEntry(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
