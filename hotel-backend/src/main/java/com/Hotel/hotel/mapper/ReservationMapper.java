package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.ReservationDTO;
import com.Hotel.hotel.models.reservation.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDto(Reservation reservation);
    Reservation toEntity(ReservationDTO reservationDTO);
}
