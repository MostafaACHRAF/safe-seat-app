package com.sqli.safeSeat.services;

import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Seat;

import java.util.List;

public interface SeatService {
    double distanceBetween(Seat seat1, Seat seat2);
    boolean isEmpty(Seat seat);
    void save(Seat seat);
    boolean canBeReserved(Floor floor, Seat seat);
    Seat findByPosition(int x, int y);
    Seat findById(int id);
    List<Seat> availableSeatsByFloor(int floorId);
    List<Seat> reservedSeatsByTeamAndFloor(int floorId, int teamId);
    List<Seat> findAll();
    double calculateMinimalValidDistanceBetweenTwoSeats(Floor floor);
    void resetSeatAvailability(Seat seat);
}
