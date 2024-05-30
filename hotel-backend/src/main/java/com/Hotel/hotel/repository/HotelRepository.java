package com.Hotel.hotel.repository;

import com.Hotel.hotel.models.hotel.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h JOIN FETCH h.rooms")
    List<Hotel> findAllWithRooms();
}
