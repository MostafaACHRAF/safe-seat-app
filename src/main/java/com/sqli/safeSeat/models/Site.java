package com.sqli.safeSeat.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@JsonIdentityInfo(property = "@id", generator = ObjectIdGenerators.UUIDGenerator.class)
public class Site {
    @Id @GeneratedValue
    private int id;
    private String name;
    @OneToMany
    private List<Floor> floors;
    @OneToMany
    private List<Employee> employees;

    public Site() {}

    public Site(String name, List<Floor> floors) {
        this.name = name;
        this.floors = floors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
