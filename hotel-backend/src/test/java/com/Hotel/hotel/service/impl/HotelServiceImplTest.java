package com.Hotel.hotel.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HotelServiceImplTest {

    private final HotelServiceImpl hotelService = new HotelServiceImpl();

    @Test
    public void testCalculateDistance() {
        double lat1 = 52.2296756;
        double lon1 = 21.0122287;
        double lat2 = 41.8919300;
        double lon2 = 12.5113300;
        double expectedDistance = 1318.44;
        double actualDistance = hotelService.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(expectedDistance, actualDistance, 5.0);
    }

    @Test
    public void testCalculateDistanceSameCoordinates() {
        double lat1 = 40.712776;
        double lon1 = -74.005974;
        double expectedDistance = 0.0;
        double actualDistance = hotelService.calculateDistance(lat1, lon1, lat1, lon1);
        assertEquals(expectedDistance, actualDistance, 0.1);
    }

    @Test
    public void testCalculateDistanceDifferentHemispheres() {
        double lat1 = 37.774929;
        double lon1 = -122.419418;
        double lat2 = -33.868820;
        double lon2 = 151.209290;
        double expectedDistance = 11948.0; // This is an approximate value
        double actualDistance = hotelService.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(expectedDistance, actualDistance, 50.0);
    }

    @Test
    public void testCalculateDistanceEquator() {
        double lat1 = 0.0;
        double lon1 = 0.0;
        double lat2 = 0.0;
        double lon2 = 10.0;
        double expectedDistance = 1113.2;
        double actualDistance = hotelService.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(expectedDistance, actualDistance, 5.0);
    }

    @Test
    public void testCalculateDistancePrimeMeridian() {
        double lat1 = 10.0;
        double lon1 = 0.0;
        double lat2 = 20.0;
        double lon2 = 0.0;
        double expectedDistance = 1112.0;
        double actualDistance = hotelService.calculateDistance(lat1, lon1, lat2, lon2);
        assertEquals(expectedDistance, actualDistance, 5.0);
    }
}
