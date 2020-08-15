package com.sqli.safeSeat.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sqli.safeSeat.enums.Team;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@JsonIdentityInfo(property = "@id", generator = ObjectIdGenerators.UUIDGenerator.class)
public class Employee {
    @Id @GeneratedValue
    private int id;
    private String name;
    private Team team;
    private int obligatoryDaysNbr;
    @OneToMany
    private List<Reservation> reservations;

    public Employee() {}

    public Employee(String name, Team team, int obligatoryDaysNbr, List<Reservation> reservations) {
        this.name = name;
        this.team = team;
        this.obligatoryDaysNbr = obligatoryDaysNbr;
        this.reservations = reservations;
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getObligatoryDaysNbr() {
        return obligatoryDaysNbr;
    }

    public void setObligatoryDaysNbr(int obligatoryDaysNbr) {
        this.obligatoryDaysNbr = obligatoryDaysNbr;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
