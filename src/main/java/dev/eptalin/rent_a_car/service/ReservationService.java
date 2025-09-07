package dev.eptalin.rent_a_car.service;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.eptalin.rent_a_car.model.*;
import dev.eptalin.rent_a_car.repository.CarRepository;
import dev.eptalin.rent_a_car.repository.ReservationRepository;
import dev.eptalin.rent_a_car.repository.UserRepository;

@Service
public class ReservationService {

    // ---- Inject Reservation, User and Car Repositories ----

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, CarRepository carRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }


    // ---- Register a New Reservation ----

    public Reservation reserveCar(Long userId, Long carId, LocalDate date) {

        // Verify user is valid
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "User not found."
                ));
        
        // Verify car is valid
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Car not found."
                ));

        // Verify date is not in the past
        if (LocalDate.now().isAfter(date)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make reservations for past dates");
        }
        
        // Verify car is available on the selected date
        reservationRepository.findByDateAndCar(date, car)
                .ifPresent(r -> {throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Car already reserved on that date.");
                });

        // If we reach here, save the reservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setDate(date);
        return reservationRepository.save(reservation);

    }

    
    // ---- Update an Existing Reservation ----

    public Reservation update(Long reservationId, Long userId, Long carId, LocalDate date) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found."));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
        
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found."));

        // Verify date is not in the past
        if (LocalDate.now().isAfter(date)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot make reservations for past dates");
        }

        // Verify car is available on the selected date (excluding this reservation)
        reservationRepository.findByDateAndCarAndIdNot(date, car, reservationId)
                .ifPresent(r -> {throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Car already reserved on that date.");
                });
        
        // If we reach this point, save the updates
        reservation.setUser(user);
        reservation.setCar(car);
        reservation.setDate(date);
        return reservationRepository.save(reservation);
    }

}
