package com.Hotel.hotel.service;

import com.Hotel.hotel.models.reservation.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ReservationService {
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation save(Reservation reservation);
    void deleteById(Long id);
    void cancelReservation(Long reservationId);
    List<Reservation> getUserReservations(Long userId);
}
