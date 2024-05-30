package com.Hotel.hotel.service;

import com.Hotel.hotel.models.reservation.ChangeRoomRequest;
import com.Hotel.hotel.models.reservation.Reservation;
import com.Hotel.hotel.models.reservation.ReservationRequest;
import com.Hotel.hotel.models.hotel.Room;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface RoomService {
    Room findById(Long id);
    List<Room> findAll();
    Room save(Room room);
    void deleteById(Long id);
    List<Room> getHotelRooms(Long hotelId);
    Reservation bookRoom(Long hotelId, ReservationRequest reservationRequest);
    void changeRoom(ChangeRoomRequest changeRoomRequest);
}
