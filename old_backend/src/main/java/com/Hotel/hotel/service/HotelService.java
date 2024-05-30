package com.Hotel.hotel.service;

import com.Hotel.hotel.models.hotel.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {
    List<Hotel> findNearbyHotels(double latitude, double longitude, double radius);
}
