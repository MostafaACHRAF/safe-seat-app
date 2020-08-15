package com.sqli.safeSeat.repositories;

import com.sqli.safeSeat.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    Seat findByXpositionAndYposition(int x, int y);
}
