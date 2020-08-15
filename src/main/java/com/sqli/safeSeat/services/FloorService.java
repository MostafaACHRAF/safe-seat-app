package com.sqli.safeSeat.services;

import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Seat;

import java.util.List;

public interface FloorService {
    Floor findById(int floorId);
    Floor findBySeat(Seat seat);
}
