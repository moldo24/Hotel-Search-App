package com.Hotel.hotel.dto;

import com.Hotel.hotel.models.hotel.RoomType;
import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private int roomNumber;
    private RoomType type;
    private float price;
    private boolean isAvailable;
    private Long hotelId;
}
