package dev.eptalin.rent_a_car.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.eptalin.rent_a_car.model.Car;
import dev.eptalin.rent_a_car.repository.CarRepository;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    // ---- DB Repository Injection ----

    private final CarRepository cars;

    public CarController(CarRepository cars) {
        this.cars = cars;
    }

    // ---- API ROUTES ----

    // GET ("/api/cars") -> Return a list of all cars
    // GET ("/api/cars?date={date}") -> Return a list of avaialble cars on {date}
    @GetMapping
    public List<Car> getCars(@RequestParam(required = false) LocalDate date) {
        // No date -> All cars
        if (date == null) {
            return cars.findAll();
        }
        // Past date -> No cars & Error
        else if (LocalDate.now().isAfter(date)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot select past dates.");
        }
        // Valid present or future date => Available cars
        else {
            return cars.findAvailableCars(date);
        }
    }

}
