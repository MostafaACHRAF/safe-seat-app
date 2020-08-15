package com.sqli.safeSeat.services.impl;

import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Seat;
import com.sqli.safeSeat.repositories.FloorRepository;
import com.sqli.safeSeat.services.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class FloorServiceImpl implements FloorService {

    private FloorRepository floorRepository;

    @Autowired
    public FloorServiceImpl(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    @Override public Floor findById(int floorId) {
        Optional<Floor> searchableFloor = this.floorRepository.findById(floorId);
        return searchableFloor.orElse(null);
    }

    @Override public Floor findBySeat(Seat seat) {
         Optional<Floor> searchableFloor =
            this.floorRepository.findAll().stream().filter(floor -> floor.getSeats().contains(seat)).findFirst();
        return searchableFloor.orElse(null);
    }
}
