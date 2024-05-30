package com.Hotel.hotel.service;

import com.Hotel.hotel.models.hotel.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface FeedbackService {
    Feedback leaveFeedback(Long hotelId, Feedback feedback);
    List<Feedback> getFeedbackForHotel(Long hotelId);
}
