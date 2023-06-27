package com.HRM.dto;

import com.HRM.models.Position;
import com.HRM.models.Unit;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PersonDTO {
    @NotEmpty
    @Size(min = 2, max = 30, message = "Firstname length should be between 2 and 30 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Lastname length should be between 2 and 30 characters")
    private String lastName;
    @Email
    private String email;

    @JsonProperty
    private boolean isActive; // represents working\fired functionality

    @NotEmpty
    @Size(min = 2, max = 30, message = "Position length should be between 2 and 30 characters")
    private String position;

    private List<String> units;

    public PersonDTO() {
    }

    public PersonDTO(String firstName, String lastName, String email, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isActive = isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }
}
