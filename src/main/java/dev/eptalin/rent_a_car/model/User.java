package dev.eptalin.rent_a_car.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @JsonIgnore // Don't serialize the password
    private String password;
    @JsonIgnore // Don't serialize the roles
    private String roles;

    // Connects Users to the foreign key column in Reservations
    @JsonIgnore // Don't serialise this attribute
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    // ---- Constructors ----

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = "ROLE_USER";
    }

    // ---- Getters & Setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    // ---- toString Method ----

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles='" + roles + '\'' +
                "}";
    }
}
