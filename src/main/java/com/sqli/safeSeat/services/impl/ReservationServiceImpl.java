package com.sqli.safeSeat.services.impl;

import com.sqli.safeSeat.converters.TeamConverter;
import com.sqli.safeSeat.enums.Availability;
import com.sqli.safeSeat.enums.Team;
import com.sqli.safeSeat.models.Employee;
import com.sqli.safeSeat.models.Reservation;
import com.sqli.safeSeat.repositories.ReservationRepository;
import com.sqli.safeSeat.services.EmployeeService;
import com.sqli.safeSeat.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private EmployeeService employeeService;

    @Autowired public ReservationServiceImpl(ReservationRepository reservationRepository,
                                             EmployeeService employeeService) {
        this.reservationRepository = reservationRepository;
        this.employeeService = employeeService;
    }

    @Override public List<Reservation> findAll() {
        return this.reservationRepository.findAll();
    }

    @Override public void save(Reservation reservation) {
        this.reservationRepository.save(reservation);
    }

    @Override public List<Reservation> findAllByTeam(int teamId) {
        List<Reservation> reservations = new ArrayList<>();
        TeamConverter teamConverter = new TeamConverter();
        Team team = teamConverter.convertToEntityAttribute(teamId);
        this.employeeService.findAll()
                .stream()
                .filter(employee -> employee.getTeam().equals(team))
                .forEach(employee -> reservations.addAll(employee.getReservations()));
        return reservations;
    }

    @Override public void deleteAll() {
        this.reservationRepository.deleteAll();
    }
}
