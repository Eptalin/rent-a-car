package dev.eptalin.rent_a_car.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.eptalin.rent_a_car.dto.UpdatedUserDetails;
import dev.eptalin.rent_a_car.dto.UserDetails;
import dev.eptalin.rent_a_car.model.User;
import dev.eptalin.rent_a_car.repository.UserRepository;
import dev.eptalin.rent_a_car.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // ---- DB Repository Injector ----

    private final UserRepository users;
    private final UserService userService;

    public UserController(UserRepository users, UserService userService) {
        this.users = users;
        this.userService = userService;
    }

    // ---- API Routes ----

    // GET ("/api/users/me") -> Return the current user
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return users.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"));
    }

    // POST ("/api/users") -> Add a new user to the repository (DB)
    @PostMapping
    public User createUser(@RequestBody UserDetails user) throws Exception {

        // Validate input
        if (user.getUsername() == null ||
                user.getPassword() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Missing required input.");
        }

        // Save the new user
        String username = user.getUsername();
        String password = user.getPassword();
        return userService.registerUser(username, password);
    }

    // PATCH ("/api/users/{id}") -> Update username or password field
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody UpdatedUserDetails request) {

        // Pass input to the User Service
        return userService.updateUser(id, request);

    }

}
