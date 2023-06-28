package com.HRM.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "unit")
public class Unit {

    // name of a unit as identity, just because I can
    @Id
    private String name;

    @Column(name = "parent_unit")
    private String parentUnit;

    // Set insteadof List because List will cause removing all records from join table before insert new
    // It happens because List could contain duplicated values
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "unit_person",
    joinColumns = @JoinColumn(name = "unit_name", referencedColumnName = "name", updatable = false, insertable = false),
    inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id", updatable = false, insertable = false))
    private Set<Person> people;

    public Unit() {
    }

    public Unit(String name, String parentUnit) {
        this.name = name;
        this.parentUnit = parentUnit;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(String parentUnit) {
        this.parentUnit = parentUnit;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", parentUnit=" + parentUnit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(name, unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
