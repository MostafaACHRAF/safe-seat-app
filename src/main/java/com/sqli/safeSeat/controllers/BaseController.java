package com.sqli.safeSeat.controllers;

import com.sqli.safeSeat.dtos.ReservationDTO;
import com.sqli.safeSeat.dtos.ReservationDetailsDTO;
import com.sqli.safeSeat.enums.Availability;
import com.sqli.safeSeat.enums.Team;
import com.sqli.safeSeat.models.Employee;
import com.sqli.safeSeat.models.Floor;
import com.sqli.safeSeat.models.Reservation;
import com.sqli.safeSeat.models.Seat;
import com.sqli.safeSeat.models.Site;
import com.sqli.safeSeat.services.EmployeeService;
import com.sqli.safeSeat.services.FloorService;
import com.sqli.safeSeat.services.ReservationService;
import com.sqli.safeSeat.services.SeatService;
import com.sqli.safeSeat.services.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController @RequestMapping("/api/v1") public class BaseController {

    private final SeatService seatService;
    private final SiteService siteService;
    private final FloorService floorService;
    private final ReservationService reservationService;
    private final EmployeeService employeeService;

    @Autowired public BaseController(SeatService seatService,
                                     SiteService siteService,
                                     FloorService floorService,
                                     ReservationService reservationService,
                                     EmployeeService employeeService) {
        this.seatService = seatService;
        this.siteService = siteService;
        this.floorService = floorService;
        this.reservationService = reservationService;
        this.employeeService = employeeService;
    }

    /**
     * Get all valid seats, that we can book
     * @return Map of valid seats by site and by floor
     */
    @GetMapping("/valid/seats") public Map<Site, Map<Floor, List<Seat>>> availableValidSeats() {
        Map<Site, Map<Floor, List<Seat>>> result = new HashMap<>();
        this.siteService.findAll().forEach(site -> {
            Map<Floor, List<Seat>> seatsPerFloor = new HashMap<>();
            for (Floor floor : site.getFloors()) {
                List<Seat> availableValidSeats = new ArrayList<>();
                for (Seat seat : floor.getSeats()) {
                    if (this.seatService.canBeReserved(floor, seat)) {
                        availableValidSeats.add(seat);
                    }
                }
                seatsPerFloor.put(floor, availableValidSeats);
            }
            result.put(site, seatsPerFloor);
        });
        return result;
    }

    /**
     * Book new seat
     * The seat should be valid
     * And the employee can't reserve multiple seats within the same period
     * @param reservationDTO reservation data access project
     * @return Response message
     */
    @PostMapping("/new/reservation") public ResponseEntity<String> reserveASeat(@RequestBody ReservationDTO reservationDTO) {
        Floor floor = this.floorService.findById(reservationDTO.getFloorId());
        Employee employee = this.employeeService.findById(reservationDTO.getEmployeeId());
        Seat seat = this.seatService.findById(reservationDTO.getSeatId());
        ResponseEntity<String> response = checkInputsValidity(employee, reservationDTO);

        if (response.getStatusCode().isError())
            return response;

        if (!this.seatService.canBeReserved(floor, seat))
            return new ResponseEntity<>("This seat can't be reserved, because there is a violation of distancing rules",
                    HttpStatus.EXPECTATION_FAILED);
        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setSeat(seat);
        this.reservationService.save(reservation);
        seat.setAvailability(Availability.RESERVED);
        this.seatService.save(seat);
        List<Reservation> employeeReservations = new ArrayList<>(employee.getReservations());
        employeeReservations.add(reservation);
        employee.setReservations(employeeReservations);
        this.employeeService.save(employee);
        return new ResponseEntity<>("Done ! Your seat has been reserved successfully.", HttpStatus.OK);
    }

    private ResponseEntity<String> checkInputsValidity(Employee employee, ReservationDTO reservationDTO) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        boolean startDateAfterOrEqualNow = reservationDTO.getStartDate().compareTo(today.getTime()) >= 0;
        boolean endDateAfterStartDate = reservationDTO.getEndDate().after(reservationDTO.getStartDate());
        boolean
                hasOtherReservationWithinTheSamePeriod =
                this.employeeService.hasReservationWithin(employee,
                        reservationDTO.getStartDate(),
                        reservationDTO.getEndDate());

        if (!startDateAfterOrEqualNow)
            return new ResponseEntity<>("Start date must be greater or equal now!", HttpStatus.EXPECTATION_FAILED);
        if (!endDateAfterStartDate)
            return new ResponseEntity<>("End date must be must be greater than start date!",
                    HttpStatus.EXPECTATION_FAILED);
        if (hasOtherReservationWithinTheSamePeriod)
            return new ResponseEntity<>("They are other reservations withing the same period!",
                    HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>("Data inputs are valid", HttpStatus.OK);
    }

    /**
     * Get all reservations details for all employees
     * @return List of reservations details
     */
    @GetMapping("/reservations/details") public List<ReservationDetailsDTO> reservationsDetails() {
        List<ReservationDetailsDTO> reservationsDetails = new ArrayList<>();
        this.siteService.findAll()
                .forEach(site -> site.getEmployees()
                        .forEach(employee -> employee.getReservations().forEach(reservation -> {
                            ReservationDetailsDTO details = new ReservationDetailsDTO();
                            details.setReservationId(reservation.getId());
                            details.setEmployeeId(employee.getId());
                            details.setEmployeeName(employee.getName());
                            details.setTeam(employee.getTeam());
                            details.setSeat(reservation.getSeat());
                            details.setStartDate(reservation.getStartDate());
                            details.setEndDate(reservation.getEndDate());
                            details.setSiteId(site.getId());
                            details.setFloorId(this.floorService.findBySeat(reservation.getSeat()).getId());
                            reservationsDetails.add(details);
                        })));
        return reservationsDetails;
    }

    /**
     * Get the nearest seats to employee's team members
     * @param teamId team id
     * @return list of valid seats
     */
    @GetMapping("/team/{teamId}/valid/seats") public Map<Site, Map<Floor, Set<Seat>>> nearestAvailableSeatsByTeam(@PathVariable String teamId) {
        Map<Site, Map<Floor, Set<Seat>>> result = new HashMap<>();

        this.siteService.findAll().forEach(site -> {
            Map<Floor, Set<Seat>> floorToSeats = new HashMap<>();
            site.getFloors().forEach(floor -> {
                List<Seat>
                        teamReservedSeats =
                        this.seatService.reservedSeatsByTeamAndFloor(floor.getId(), Integer.parseInt(teamId));
                List<Seat>
                        floorAvailableValidSeats =
                        this.seatService.availableSeatsByFloor(floor.getId())
                                .stream()
                                .filter(seat -> this.seatService.canBeReserved(floor, seat))
                                .collect(Collectors.toList());
                double minimalValidDistance = this.seatService.calculateMinimalValidDistanceBetweenTwoSeats(floor);
                Set<Seat> nearestSeats = new HashSet<>();
                for (Seat reservedSeat : teamReservedSeats) {
                    for (Seat availableSeat : floorAvailableValidSeats) {
                        double distance = this.seatService.distanceBetween(availableSeat, reservedSeat);
                        if (distance == minimalValidDistance) {
                            nearestSeats.add(availableSeat);
                        }
                    }
                }
                floorToSeats.put(floor, nearestSeats);
            });
            result.put(site, floorToSeats);
        });
        return result;
    }

    /**
     * Get reservations details for one employee
     * @param employeeId employee identifier
     * @return reservation details
     */
    @GetMapping("/user/{employeeId}/reservations") public List<ReservationDetailsDTO> reservationsByEmployee(@PathVariable String employeeId) {
        return this.reservationsDetails()
                .stream()
                .filter(details -> details.getEmployeeId() == Integer.parseInt(employeeId))
                .collect(Collectors.toList());
    }

    /**
     * Get all seats
     * @return list of seats
     */
    @GetMapping("/all/seats") public List<Seat> getAllSeats() {
        return this.seatService.findAll();
    }

    /**
     * Get all employees
     * @return list of employees
     */
    @GetMapping("/all/employees") public List<Employee> getAllEmployees() {
        return this.employeeService.findAll();
    }

    /**
     * Get all teams
     * @return list of teams
     */
    @GetMapping("/all/teams") public List<Team> getAllTeams() {
        return Arrays.asList(Team.values());
    }

    /**
     * Get all sites
     * @return list of sites
     */
    @GetMapping("/all/sites") public List<Site> getAllSites() {
        return this.siteService.findAll();
    }

    /**
     * Get all floors
     * @return list of floors
     */
    @GetMapping("/all/floors") public List<Floor> getAllFloors() {
        return this.floorService.findAll();
    }

    /**
     * Reset reserved seats availability
     * Then delete all reservations
     * @return Response message
     */
    @Transactional @DeleteMapping("delete/all/reservations") public ResponseEntity<String> deleteAllReservations() {
        this.reservationService.findAll()
                .forEach(reservation -> this.seatService.resetSeatAvailability(reservation.getSeat()));
        this.employeeService.findAll().forEach(employee -> {
            employee.setReservations(new ArrayList<>());
            this.employeeService.save(employee);
        });
        this.reservationService.deleteAll();
        return new ResponseEntity<>("All reservations are deleted successfully.", HttpStatus.OK);
    }
}
