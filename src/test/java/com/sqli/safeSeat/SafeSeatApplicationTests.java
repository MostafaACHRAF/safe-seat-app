package com.sqli.safeSeat;

import com.sqli.safeSeat.enums.Availability;
import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Seat;
import com.sqli.safeSeat.repositories.SeatRepository;
import com.sqli.safeSeat.services.SeatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest class SafeSeatApplicationTests {

    private final SeatService seatService;

    @Autowired public SafeSeatApplicationTests(SeatService seatService) {
        this.seatService = seatService;
    }

    @Test void shouldGetAllSeats() {
        final long nbrOfSeats = this.seatService.findAll().stream().count();
        Assertions.assertThat(nbrOfSeats).isEqualTo(12);
    }

    @Test void shouldGetTheEmptySeat() {
        Seat emptySeat = new Seat(0, 0, Availability.AVAILABLE);
        Assertions.assertThat(this.seatService.isEmpty(emptySeat)).isEqualTo(true);
    }

}
