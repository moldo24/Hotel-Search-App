package com.Hotel.hotel.service.impl;

import com.Hotel.hotel.models.hotel.Hotel;
import com.Hotel.hotel.repository.HotelRepository;
import com.Hotel.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override // using the formula to find nearby hotels
    public List<Hotel> findNearbyHotels(double userLat, double userLon, double radius) {
        List<Hotel> allHotels = hotelRepository.findAll();
        List<Hotel> nearbyHotels = new ArrayList<>();

        for (Hotel hotel : allHotels) {
            double distance = calculateDistance(userLat, userLon, hotel.getLatitude(), hotel.getLongitude());
            if (distance <= radius) {
                nearbyHotels.add(hotel);
            }
        }
        return nearbyHotels;
    }
    @Override
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
}
