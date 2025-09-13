package dev.eptalin.rent_a_car.controller;

import java.time.LocalDate;
import java.util.List;
import dev.eptalin.rent_a_car.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.eptalin.rent_a_car.dto.ReservationDetails;
import dev.eptalin.rent_a_car.model.Reservation;
import dev.eptalin.rent_a_car.model.User;
import dev.eptalin.rent_a_car.repository.CarRepository;
import dev.eptalin.rent_a_car.repository.ReservationRepository;
import dev.eptalin.rent_a_car.repository.UserRepository;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    // ---- DB Repository Injection ----

    private final ReservationRepository reservations;
    private final ReservationService reservationService;
    private final UserRepository users;

    public ReservationController(ReservationRepository reservations, CarRepository carRepository,
            ReservationService reservationService, UserRepository users) {
        this.reservations = reservations;
        this.reservationService = reservationService;
        this.users = users;
    }

    // ---- API Routes ----

    // GET ("/api/reservations") -> Return a list of all reservations
    // GET ("/api/reservations?userId={id}") -> Return a list of one user's
    // reservations
    @GetMapping
    public List<Reservation> getReservations(@RequestParam(required = false) Long userId) {
        // No userId -> All reservations
        if (userId == null) {
            return reservations.findAll();
        }
        // userId -> User's reservations
        else {
            User user = users.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "User not found."));
            return reservations.findByUserOrderByDateDesc(user);
        }
    }

    // GET ("/api/reservations/{id}") -> Return a single reservation
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservations.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reservation not found"));
    }

    // POST ("/api/reservations") -> Create a new reservation
    @PostMapping
    public Reservation addReservation(@RequestBody ReservationDetails reservation) {

        // Validate input
        if (reservation.getUserId() == null ||
                reservation.getCarId() == null ||
                reservation.getDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Missing required input.");
        }

        // Save the new reservation
        Long userId = reservation.getUserId();
        Long carId = reservation.getCarId();
        LocalDate date = reservation.getDate();
        return reservationService.reserveCar(userId, carId, date);
    }

    // PUT ("/api/reservations/{id}") -> Alter an existing reservation
    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id, @RequestBody ReservationDetails reservation) {

        // Validate input
        if (reservation.getUserId() == null ||
                reservation.getCarId() == null ||
                reservation.getDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Missing required input.");
        }

        // Save the new reservation
        Long reservationId = id;
        Long userId = reservation.getUserId();
        Long carId = reservation.getCarId();
        LocalDate date = reservation.getDate();
        return reservationService.update(reservationId, userId, carId, date);
    }

    // DELETE ("/api/reservations/{id}") -> Delete an existing reservation
    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        if (!reservations.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation not found.");
        }
        reservations.deleteById(id);
    }
}
