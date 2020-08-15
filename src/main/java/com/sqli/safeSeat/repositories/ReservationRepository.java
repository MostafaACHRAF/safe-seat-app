package com.sqli.safeSeat.repositories;

import com.sqli.safeSeat.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {}
