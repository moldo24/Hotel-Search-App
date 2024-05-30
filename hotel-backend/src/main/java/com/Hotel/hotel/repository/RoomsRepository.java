package com.Hotel.hotel.repository;

import com.Hotel.hotel.models.hotel.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomsRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelId(Long hotelId);

    Optional<Room> findByRoomNumberAndHotelId(Integer newRoomNumber, Long hotelId);
}
