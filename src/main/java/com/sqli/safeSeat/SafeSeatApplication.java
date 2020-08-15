package com.sqli.safeSeat;

import com.sqli.safeSeat.enums.Availability;
import com.sqli.safeSeat.enums.Team;
import com.sqli.safeSeat.models.Employee;
import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Seat;
import com.sqli.safeSeat.models.Site;
import com.sqli.safeSeat.repositories.EmployeeRepository;
import com.sqli.safeSeat.repositories.FloorRepository;
import com.sqli.safeSeat.repositories.ReservationRepository;
import com.sqli.safeSeat.repositories.SeatRepository;
import com.sqli.safeSeat.repositories.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication public class SafeSeatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafeSeatApplication.class, args);
    }

    @Component
    class Runner implements CommandLineRunner {

        private EmployeeRepository employeeRepository;
        private SiteRepository siteRepository;
        private FloorRepository floorRepository;
        private SeatRepository seatRepository;
        private ReservationRepository reservationRepository;

        @Autowired
        public Runner(EmployeeRepository employeeRepository,
                      SiteRepository siteRepository,
                      FloorRepository floorRepository,
                      SeatRepository seatRepository,
                      ReservationRepository reservationRepository) {
            this.employeeRepository = employeeRepository;
            this.siteRepository = siteRepository;
            this.floorRepository = floorRepository;
            this.seatRepository = seatRepository;
            this.reservationRepository = reservationRepository;
        }

        @Override public void run(String... args) throws Exception {
            Employee e1 = new Employee("mostafa", Team.SEB, 2, null);
            Employee e2 = new Employee("anas", Team.VOO, 1, null);
            Employee e3 = new Employee("khalid", Team.SEB, 3, null);
            Employee e4 = new Employee("yahya", Team.SEB, 0, null);
            this.employeeRepository.saveAll(Arrays.asList(e1, e2, e3, e4));

            Seat s1 = new Seat(0, 0, Availability.AVAILABLE);
            Seat s2 = new Seat(10, 0, Availability.AVAILABLE);
            Seat s3 = new Seat(20, 0, Availability.AVAILABLE);
            Seat s4 = new Seat(30, 0, Availability.AVAILABLE);
            Seat s5 = new Seat(0, 10, Availability.AVAILABLE);
            Seat s6 = new Seat(10, 10, Availability.AVAILABLE);
            Seat s7 = new Seat(20, 10, Availability.AVAILABLE);
            Seat s8 = new Seat(30, 10, Availability.AVAILABLE);
            Seat s9 = new Seat(0, 20, Availability.AVAILABLE);
            Seat s10 = new Seat(10, 20, Availability.AVAILABLE);
            Seat s11 = new Seat(20, 20, Availability.AVAILABLE);
            Seat s12 = new Seat(30, 20, Availability.AVAILABLE);

            this.seatRepository.saveAll(Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12));

            Floor f1 = new Floor(1, Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12));
            this.floorRepository.save(f1);

            Site site = new Site("Rabat", Collections.singletonList(f1));
            site.setEmployees(Arrays.asList(e1, e2, e3, e4));
            this.siteRepository.save(site);
        }
    }
}
