package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.HotelDTO;
import com.Hotel.hotel.models.hotel.Hotel;
import org.springframework.stereotype.Component;

public class HotelMapper {

    public HotelDTO toDto(Hotel hotel) {
        if (hotel == null) {
            return null;
        }

        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setId(hotel.getId());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setLatitude(hotel.getLatitude());
        hotelDTO.setLongitude(hotel.getLongitude());

        return hotelDTO;
    }
}
