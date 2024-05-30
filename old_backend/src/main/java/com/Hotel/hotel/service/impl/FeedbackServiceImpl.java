package com.Hotel.hotel.service.impl;

import com.Hotel.hotel.models.hotel.Feedback;
import com.Hotel.hotel.repository.FeedbackRepository;
import com.Hotel.hotel.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Override
    public Feedback leaveFeedback(Long hotelId, Feedback feedback) {
        feedback.setHotelId(hotelId);
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getFeedbackForHotel(Long hotelId) {
        return feedbackRepository.findByHotelId(hotelId);
    }

}
