package com.Hotel.hotel.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Long id;
    private String email;
    private Long userId;
    private Long hotelId;
    private String comments;
    private int cleanlinessRating;
    private int serviceRating;
}
