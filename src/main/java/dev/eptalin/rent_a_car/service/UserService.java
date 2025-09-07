package dev.eptalin.rent_a_car.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.eptalin.rent_a_car.dto.UpdatedUserDetails;
import dev.eptalin.rent_a_car.model.User;
import dev.eptalin.rent_a_car.repository.UserRepository;

@Service
public class UserService {
    
    // ---- Inject User Repository and Password Encoder
    
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        
    }


    // ---- Register a New User ----

    public User registerUser(String username, String password) throws Exception {

        // Check if username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        // Encode Password
        String encodedPassword = encoder.encode(password);

        // Save new user
        User user = new User(username, encodedPassword);
        return userRepository.save(user);

    }


    // ---- Update an Existing User ----
    
    public User updateUser(Long id, UpdatedUserDetails request) {
        
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Update the username
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        // Update the password
        if (request.getOldPassword() != null && request.getNewPassword() != null) {

            // Ensure the OldPassword matches the current one
            if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect.");
            }
            
            // Encode and save the new password
            user.setPassword(encoder.encode(request.getNewPassword()));
        }

        // Save updated user
        return userRepository.save(user);

    }

    
}
