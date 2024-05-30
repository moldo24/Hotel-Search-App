package com.Hotel.hotel.models.reservation;

import lombok.Data;

@Data
public class ChangeRoomRequest {
    private Long reservationId;
    private Integer newRoomNumber;
    private Long hotelId;

}
