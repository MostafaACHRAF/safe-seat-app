package com.sqli.safeSeat.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sqli.safeSeat.enums.Availability;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@JsonIdentityInfo(property = "@id", generator = ObjectIdGenerators.UUIDGenerator.class)
public class Seat {
    @Id @GeneratedValue
    private int id;
    private int xposition;
    private int yposition;
    private Availability availability;

    public Seat() {}

    public Seat(int xposition, int yposition, Availability availability) {
        this.xposition = xposition;
        this.yposition = yposition;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getxposition() {
        return xposition;
    }

    public void setxposition(int xposition) {
        this.xposition = xposition;
    }

    public int getyposition() {
        return yposition;
    }

    public void setyposition(int yposition) {
        this.yposition = yposition;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }
}
