package com.HRM.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "unit")
public class Unit {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(name = "name")
    private String name;

    @Column(name = "parent_unit")
    private String parentUnit;
    @ManyToMany
    @JoinTable(name = "unit_person",
    joinColumns = @JoinColumn(name = "unit_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> people;

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

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", parentUnit=" + parentUnit +
                '}';
    }
}
