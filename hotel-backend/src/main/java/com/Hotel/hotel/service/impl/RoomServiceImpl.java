package com.Hotel.hotel.service.impl;
import com.Hotel.hotel.models.*;
import com.Hotel.hotel.models.hotel.Room;
import com.Hotel.hotel.models.reservation.ChangeRoomRequest;
import com.Hotel.hotel.models.reservation.Reservation;
import com.Hotel.hotel.models.reservation.ReservationRequest;
import com.Hotel.hotel.repository.ReservationRepository;
import com.Hotel.hotel.repository.RoomsRepository;
import com.Hotel.hotel.repository.UserRepository;
import com.Hotel.hotel.service.RoomService;
import com.Hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomsRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    public Room findById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.orElse(null);
    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }
    @Override
    public List<Room> getHotelRooms(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }

    @Override
    public Reservation bookRoom(Long hotelId, ReservationRequest reservationRequest) {
        Optional<Room> roomOpt = roomRepository.findById(reservationRequest.getRoomId());
        if (!roomOpt.isPresent()) {
            throw new IllegalArgumentException("Room not found");
        }

        Room room = roomOpt.get();
        if (!room.isAvailable()) {
            throw new IllegalArgumentException("Room is not available");
        }

        User user = userService.getUserById(reservationRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Reservation reservation = new Reservation();
        reservation.setUserId(user.getId());
        reservation.setRoomId(room.getId());
        reservation.setCheckIn(reservationRequest.getCheckIn());
        reservation.setCheckOut(reservationRequest.getCheckOut());

        room.setAvailable(false);
        roomRepository.save(room);

        return reservationRepository.save(reservation);
    }

    @Override
    public void changeRoom(ChangeRoomRequest changeRoomRequest) {
        Reservation reservation = reservationRepository.findById(changeRoomRequest.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        Room newRoom = roomRepository.findByRoomNumberAndHotelId(changeRoomRequest.getNewRoomNumber(), changeRoomRequest.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found or not available"));
        if (LocalDateTime.now().isAfter(reservation.getCheckIn().minusHours(2))) {
            throw new IllegalArgumentException("Cannot cancel reservation within two hours of check-in");
        } else if (newRoom.isAvailable()) {
            Room oldRoom = roomRepository.findById(reservation.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Old room not found"));
            oldRoom.setAvailable(true);
            roomRepository.save(oldRoom);

            newRoom.setAvailable(false);
            roomRepository.save(newRoom);

            reservation.setRoomId(newRoom.getId());
            reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("New room is not available");
        }
    }

}
