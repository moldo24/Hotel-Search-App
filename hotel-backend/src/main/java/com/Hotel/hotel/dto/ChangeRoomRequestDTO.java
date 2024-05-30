package com.Hotel.hotel.dto;

import lombok.Data;

@Data
public class ChangeRoomRequestDTO {
    private Long reservationId;
    private Integer newRoomNumber;
    private Long hotelId;
}
