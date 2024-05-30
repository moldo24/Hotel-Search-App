package com.Hotel.hotel.controllers;

import com.Hotel.hotel.dto.FeedbackDTO;
import com.Hotel.hotel.mapper.FeedbackMapper;
import com.Hotel.hotel.models.User;
import com.Hotel.hotel.models.hotel.Feedback;
import com.Hotel.hotel.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @PostMapping("/{hotelId}")
    public FeedbackDTO leaveFeedback(@PathVariable Long hotelId, @RequestBody FeedbackDTO feedbackDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
        feedback.setUserId(user.getId());
        feedback.setEmail(user.getEmail());
        Feedback savedFeedback = feedbackService.leaveFeedback(hotelId, feedback);
        return feedbackMapper.toDto(savedFeedback);
    }

    @GetMapping("/{hotelId}")
    public List<FeedbackDTO> getFeedbackForHotel(@PathVariable Long hotelId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackForHotel(hotelId);
        return feedbackList.stream().map(feedbackMapper::toDto).collect(Collectors.toList());
    }
}
