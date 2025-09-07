package dev.eptalin.rent_a_car.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(
    name = "reservations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"car_id", "date"})
    })
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Define a many-to-one relationship
    // Reference the foreign key entity
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private LocalDate date;


    // ---- Constructors ----

    public Reservation() { }

    public Reservation(User user, Car car, LocalDate date) {
        this.user = user;
        this.car = car;
        this.date = date;
    }


    // ---- Getters & Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }


    // ---- String Method ----

    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", car='" + car + '\'' +
                ", date=" + date +
                "}";
    }

}
