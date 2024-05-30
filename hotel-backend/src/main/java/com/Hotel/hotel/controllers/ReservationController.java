package com.Hotel.hotel.controllers;

import com.Hotel.hotel.dto.ChangeRoomRequestDTO;
import com.Hotel.hotel.dto.ReservationDTO;
import com.Hotel.hotel.dto.ReservationRequestDTO;
import com.Hotel.hotel.mapper.ChangeRoomRequestMapper;
import com.Hotel.hotel.mapper.ReservationMapper;
import com.Hotel.hotel.mapper.ReservationRequestMapper;
import com.Hotel.hotel.models.User;
import com.Hotel.hotel.models.reservation.ChangeRoomRequest;
import com.Hotel.hotel.models.reservation.Reservation;
import com.Hotel.hotel.models.reservation.ReservationRequest;
import com.Hotel.hotel.service.ReservationService;
import com.Hotel.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationRequestMapper reservationRequestMapper;

    @Autowired
    private ChangeRoomRequestMapper changeRoomRequestMapper;

    @PostMapping("/{hotelId}")
    public ReservationDTO bookRoom(@PathVariable Long hotelId, @RequestBody ReservationRequestDTO reservationRequestDTO, Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        ReservationRequest reservationRequest = reservationRequestMapper.toEntity(reservationRequestDTO);
        reservationRequest.setUserId(userId);
        Reservation reservation = roomService.bookRoom(hotelId, reservationRequest);
        return reservationMapper.toDto(reservation);
    }

    @PostMapping("/changeRoom")
    public ResponseEntity<String> changeRoom(@RequestBody ChangeRoomRequestDTO changeRoomRequestDTO) {
        ChangeRoomRequest changeRoomRequest = changeRoomRequestMapper.toEntity(changeRoomRequestDTO);
        roomService.changeRoom(changeRoomRequest);
        return ResponseEntity.ok("Room changed successfully.");
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok("Reservation cancelled successfully.");
    }

    @GetMapping
    public List<ReservationDTO> getUserReservations(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        List<Reservation> reservations = reservationService.getUserReservations(userId);
        return reservations.stream().map(reservationMapper::toDto).collect(Collectors.toList());
    }
}
