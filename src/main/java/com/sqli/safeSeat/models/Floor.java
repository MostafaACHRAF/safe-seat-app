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
public class Floor {
    @Id @GeneratedValue
    private int id;
    private int number;
    @OneToMany
    private List<Seat> seats;

    public Floor() {}

    public Floor(int number, List<Seat> seats) {
        this.number = number;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
