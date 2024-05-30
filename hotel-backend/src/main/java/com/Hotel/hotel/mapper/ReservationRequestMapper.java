package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.ReservationRequestDTO;
import com.Hotel.hotel.models.reservation.ReservationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationRequestMapper {
    ReservationRequestDTO toDto(ReservationRequest reservationRequest);
    ReservationRequest toEntity(ReservationRequestDTO reservationRequestDTO);
}
