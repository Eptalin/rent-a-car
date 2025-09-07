package dev.eptalin.rent_a_car;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// For command line imports
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Random;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import dev.eptalin.rent_a_car.model.*;
// import dev.eptalin.rent_a_car.repository.*;

@SpringBootApplication
public class RentACarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentACarApplication.class, args);
	}

	
	// ---- Run Commands on Server Startup ----
	
	// @Bean
	// CommandLineRunner commandLineRunner(
	// 	UserRepository users, 
	// 	PasswordEncoder encoder, 
	// 	CarRepository cars,
	// 	ReservationRepository reservations) {
	// 	return args -> {
	// 		// Create a sample user
	// 		// users.save(new User("user", encoder.encode("password")));

	// 		// Create sample reservations
	// 		List<User> allUsers = users.findAll();
	// 		List<Car> allCars = cars.findAll();

	//		// Choose start and end dates for random entries
	// 		LocalDate start = LocalDate.of(2025, 9, 15);
	// 		LocalDate end   = LocalDate.of(2025, 9, 21);

	// 		// Loop through each date in the range
	// 		for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
	// 			List<Car> shuffledCars = new ArrayList<>(allCars);
	// 			//randomize cars each day
	// 			Collections.shuffle(shuffledCars); 
	// 			// pick a number of cars to reserve for the day (10 - 20)
	// 			Random rand = new Random();
	// 			int carsToReserve = 10 + rand.nextInt(11);
	// 			for (int i = 0; i < carsToReserve; i++) {
	// 				Car car = shuffledCars.get(i);
	// 				User user = allUsers.get(new Random().nextInt(allUsers.size()));

	// 				Reservation reservation = new Reservation(user, car, date);
	// 				reservations.save(reservation);
	// 			}
	// 		}
	// 	};
	// }

}
