package com.sensor.SensorServer.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorUserDTO {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 3, max = 30, message = "Username should be 3 to 30 characters")
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
