package com.sqli.safeSeat.services;

import com.sqli.safeSeat.models.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    void save(Reservation reservation);
    List<Reservation> findAllByTeam(int teamId);
    void deleteAll();
}
