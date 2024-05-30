package com.Hotel.hotel.controllers;

import com.Hotel.hotel.dto.HotelDTO;
import com.Hotel.hotel.dto.RoomDTO;
import com.Hotel.hotel.mapper.HotelMapper;
import com.Hotel.hotel.mapper.RoomMapper;
import com.Hotel.hotel.models.hotel.Feedback;
import com.Hotel.hotel.models.hotel.Hotel;
import com.Hotel.hotel.models.hotel.Room;
import com.Hotel.hotel.service.FeedbackService;
import com.Hotel.hotel.service.HotelService;
import com.Hotel.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/{hotelId}/feedback")
    public ResponseEntity<Void> leaveFeedback(@PathVariable Long hotelId, @RequestBody Feedback feedback) {
        feedbackService.leaveFeedback(hotelId, feedback);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/search")
    public List<HotelDTO> findNearbyHotels(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double radius) {
        HotelMapper hotelMapper = new HotelMapper();
        List<Hotel> hotels = hotelService.findNearbyHotels(latitude, longitude, radius);
        return hotels.stream().map(hotelMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{hotelId}/rooms")
    public List<RoomDTO> getHotelRooms(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getHotelRooms(hotelId);
        return rooms.stream().map(roomMapper::toDto).collect(Collectors.toList());
    }
}
