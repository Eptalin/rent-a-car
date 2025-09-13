package dev.eptalin.rent_a_car.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.eptalin.rent_a_car.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Create a custom method to search users in the DB by username
    Optional<User> findByUsername(String username);

}
