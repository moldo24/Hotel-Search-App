package com.Hotel.hotel.service.impl;

import com.Hotel.hotel.models.hotel.Hotel;
import com.Hotel.hotel.models.reservation.Reservation;
import com.Hotel.hotel.models.hotel.Room;
import com.Hotel.hotel.repository.HotelRepository;
import com.Hotel.hotel.repository.ReservationRepository;
import com.Hotel.hotel.repository.RoomsRepository;
import com.Hotel.hotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getUserReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findByUserId(userId);
        for (Reservation reservation : reservations) {
            Room room = roomsRepository.findById(reservation.getRoomId())
                    .orElseThrow(() -> new IllegalArgumentException("Room not found"));
            Hotel hotel = hotelRepository.findById(room.getHotel().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
            reservation.setHotel(hotel);
            reservation.setRoomNumber(room.getRoomNumber()); // Set room number
        }
        return reservations;
    }

    @Override
    public void cancelReservation(Long reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        if (!reservationOpt.isPresent()) {
            throw new IllegalArgumentException("Reservation not found");
        }

        Reservation reservation = reservationOpt.get();
        if (LocalDateTime.now().isAfter(reservation.getCheckIn().minusHours(2))) {
            throw new IllegalArgumentException("Cannot cancel reservation within two hours of check-in");
        }

        Room room = roomsRepository.findById(reservation.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        room.setAvailable(true);
        roomsRepository.save(room);

        reservationRepository.delete(reservation);
    }
}
