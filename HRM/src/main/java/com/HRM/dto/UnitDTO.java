package com.HRM.dto;

public class UnitDTO {
    private String name;
    private UnitDTO parentUnit;

    public UnitDTO() {
    }

    public UnitDTO(String name, UnitDTO parentUnit) {
        this.name = name;
        this.parentUnit = parentUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UnitDTO getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(UnitDTO parentUnit) {
        this.parentUnit = parentUnit;
    }
}
