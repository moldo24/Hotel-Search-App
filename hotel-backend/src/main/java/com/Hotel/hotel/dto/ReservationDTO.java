package com.Hotel.hotel.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private HotelDTO hotel;
    private Integer roomNumber;
}
