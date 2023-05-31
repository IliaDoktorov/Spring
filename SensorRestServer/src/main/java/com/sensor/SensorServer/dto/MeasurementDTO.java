package com.sensor.SensorServer.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sensor.SensorServer.models.Sensor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Digits;

public class MeasurementDTO {
    @Digits(integer = 2, fraction = 1, message = "Value should have two-digit integer value and one-digit fractional")
    private float value;

    private boolean raining;

    private Sensor sensor;

    public MeasurementDTO() {
    }

    public MeasurementDTO(float value, boolean raining, Sensor sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
