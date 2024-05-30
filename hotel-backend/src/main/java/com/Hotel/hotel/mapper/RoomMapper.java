package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.RoomDTO;
import com.Hotel.hotel.models.hotel.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomDTO toDto(Room room);
    Room toEntity(RoomDTO roomDTO);
}
