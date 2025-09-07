package dev.eptalin.rent_a_car.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.eptalin.rent_a_car.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {

    // List cars with no Reservation on {date}
    @Query("SELECT c FROM Car c WHERE c.id NOT IN (SELECT r.car.id FROM Reservation r WHERE r.date = :date)")
    List<Car> findAvailableCars(@Param("date") LocalDate date);
    

    // Use Optional<T> when you expect 1 result.
    // Eg: Optional<User> findByUsername(String username);
    
    // Use List<T> when you expect 0, 1, or many results.
    // Eg: List<Reservation> findByUserIdAndCarId(Long userId, Long carId);
    // A user could reserve the same car multiple times on different dates.

}