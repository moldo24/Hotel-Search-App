package com.Hotel.hotel.mapper;

import com.Hotel.hotel.dto.FeedbackDTO;
import com.Hotel.hotel.models.hotel.Feedback;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {
    FeedbackDTO toDto(Feedback feedback);
    Feedback toEntity(FeedbackDTO feedbackDTO);
}
