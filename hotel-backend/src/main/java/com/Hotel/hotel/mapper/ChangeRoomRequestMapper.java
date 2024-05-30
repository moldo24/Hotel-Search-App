package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.ChangeRoomRequestDTO;
import com.Hotel.hotel.models.reservation.ChangeRoomRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangeRoomRequestMapper {
    ChangeRoomRequestDTO toDto(ChangeRoomRequest changeRoomRequest);
    ChangeRoomRequest toEntity(ChangeRoomRequestDTO changeRoomRequestDTO);
}
