package com.Hotel.hotel.repository;

import com.Hotel.hotel.models.hotel.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByHotelId(Long hotelId);
}
