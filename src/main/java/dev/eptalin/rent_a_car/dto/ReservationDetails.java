package dev.eptalin.rent_a_car.dto;

import java.time.LocalDate;


public class ReservationDetails {
    
    private Long userId;
    private Long carId;
    private LocalDate date;


    // ---- Constructors ----

    public ReservationDetails() { }

    public ReservationDetails(Long userId, Long carId, LocalDate date) {
        this.userId = userId;
        this.carId = carId;
        this.date = date;
    }


    // ---- Getters & Setters ----

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

}
