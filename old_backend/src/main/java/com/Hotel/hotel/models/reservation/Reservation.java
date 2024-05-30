package com.Hotel.hotel.models.reservation;

import com.Hotel.hotel.models.hotel.Hotel;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long roomId;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @Transient
    private Hotel hotel;

    @Transient
    private Integer roomNumber;
}
