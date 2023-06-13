package com.HRM.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "unit")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "parent_unit")
    private Unit parentUnit;
    @ManyToMany
    @JoinTable(name = "unit_person",
    joinColumns = @JoinColumn(name = "unit_id"),
    inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> people;

}
