package com.Hotel.hotel;

import com.Hotel.hotel.models.*;
import com.Hotel.hotel.models.hotel.Hotel;
import com.Hotel.hotel.models.hotel.Room;
import com.Hotel.hotel.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.util.List;

@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder, HotelRepository hotelRepository, RoomsRepository roomsRepository, FeedbackRepository feedbackRepository) {
		return args -> {
			if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
				User admin = new User();
				admin.setEmail("admin@admin.com");
				admin.setPassword(passwordEncoder.encode("password"));
				userRepository.save(admin);
			}

			if (hotelRepository.count() == 0) {
				loadHotelsAndRoomsFromJson(hotelRepository, roomsRepository);
			}
		};
	}

	private void loadHotelsAndRoomsFromJson(HotelRepository hotelRepository, RoomsRepository roomsRepository) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		List<Hotel> hotels = mapper.readValue(new File("src/main/java/com/Hotel/hotel/hotels.json"), mapper.getTypeFactory().constructCollectionType(List.class, Hotel.class));
		for (Hotel hotel : hotels) {
			Hotel savedHotel = hotelRepository.save(hotel);
			for (Room room : hotel.getRooms()) {
				room.setHotel(savedHotel);
				roomsRepository.save(room);
			}
		}
	}
}
