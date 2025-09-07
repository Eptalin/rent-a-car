package dev.eptalin.rent_a_car.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eptalin.rent_a_car.model.Car;
import dev.eptalin.rent_a_car.model.Reservation;
import dev.eptalin.rent_a_car.model.User;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Generate a List of reservations by date
    List<Reservation> findByDate(LocalDate date);

    // Generate a List of reservations for a specific user in reverse order
    List<Reservation> findByUserOrderByDateDesc(User user);

    // Find a reservation for a car on a date
    Optional<Reservation> findByDateAndCar(LocalDate date, Car car);
    
    // Find a reservation for a car on a date, excluding a reservation we're editing
    Optional<Reservation> findByDateAndCarAndIdNot(LocalDate date, Car car, Long id);
    
}
