package com.Hotel.hotel.controllers;

import com.Hotel.hotel.models.User;
import com.Hotel.hotel.models.hotel.Feedback;
import com.Hotel.hotel.models.hotel.Hotel;
import com.Hotel.hotel.models.hotel.Room;
import com.Hotel.hotel.models.reservation.ChangeRoomRequest;
import com.Hotel.hotel.models.reservation.Reservation;
import com.Hotel.hotel.models.reservation.ReservationRequest;
import com.Hotel.hotel.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    private static final Logger logger = Logger.getLogger(HotelController.class.getName());

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FeedbackService feedbackService;

    private UserService userService;

    @GetMapping("/search")
    public String findNearbyHotels(
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Double radius,
            Model model) {
        if (latitude == null || longitude == null || radius == null) {
            return "search";
        }
        List<Hotel> hotels = hotelService.findNearbyHotels(latitude, longitude, radius);
        model.addAttribute("hotels", hotels);
        model.addAttribute("searchLatitude", latitude);
        model.addAttribute("searchLongitude", longitude);

        return "hotels";
    }

    @GetMapping("/{hotelId}/rooms")
    public String getHotelRooms(@PathVariable Long hotelId, Model model) {
        List<Room> rooms = roomService.getHotelRooms(hotelId);
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @PostMapping("/{hotelId}/reservation")
    public String bookRoom(@PathVariable Long hotelId, @ModelAttribute ReservationRequest reservationRequest, Model model, Authentication authentication) {
        try {
            Long userId = ((User) authentication.getPrincipal()).getId();
            reservationRequest.setUserId(userId);

            Reservation reservation = roomService.bookRoom(hotelId, reservationRequest);
            Room room = roomService.findById(reservation.getRoomId());
            Hotel hotel = room.getHotel();

            model.addAttribute("reservation", reservation);
            model.addAttribute("room", room);
            model.addAttribute("hotel", hotel);

            return "reservationConfirmation";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
    }

    @PostMapping("/reservations/changeRoom")
    @ResponseBody
    public ResponseEntity<String> changeRoom(@RequestBody ChangeRoomRequest changeRoomRequest) {
        logger.log(Level.INFO, "Request to change room: {0}", changeRoomRequest);
        try {
            roomService.changeRoom(changeRoomRequest);
            logger.log(Level.INFO, "Room changed successfully.");
            return ResponseEntity.ok("Room changed successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to change the room: {0}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to change the room: " + e.getMessage());
        }
    }

    @DeleteMapping("/reservation/{reservationId}")
    @ResponseBody
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        logger.log(Level.INFO, "Request to cancel reservation: {0}", reservationId);
        try {
            reservationService.cancelReservation(reservationId);
            logger.log(Level.INFO, "Reservation cancelled successfully.");
            return ResponseEntity.ok("Reservation cancelled successfully.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to cancel reservation: {0}", e.getMessage());
            return ResponseEntity.status(500).body("Failed to cancel reservation: " + e.getMessage());
        }
    }

    @PostMapping("/{hotelId}/feedback")
    public String leaveFeedback(@PathVariable Long hotelId, @ModelAttribute Feedback feedback, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        feedback.setUserId(user.getId());
        feedback.setEmail(user.getEmail());
        Feedback savedFeedback = feedbackService.leaveFeedback(hotelId, feedback);
        model.addAttribute("feedback", new Feedback()); // Add an empty feedback object to reset the form
        model.addAttribute("hotelId", hotelId); // Ensure hotelId is available in the template
        return "leaveFeedback";
    }

    @GetMapping("/{hotelId}/feedback")
    public String viewFeedback(@PathVariable Long hotelId, Model model) {
        List<Feedback> feedbackList = feedbackService.getFeedbackForHotel(hotelId);
        model.addAttribute("feedbackList", feedbackList);
        return "viewFeedback";
    }

    @GetMapping("/{hotelId}/leave-feedback")
    public String showFeedbackForm(@PathVariable Long hotelId, Model model) {
        model.addAttribute("hotelId", hotelId);
        model.addAttribute("feedback", new Feedback());
        return "leaveFeedback";
    }

    @GetMapping("/reservations")
    public String getUserReservations(Model model, Authentication authentication, HttpServletRequest request) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        List<Reservation> reservations = reservationService.getUserReservations(userId);
        model.addAttribute("reservations", reservations);

        // Add CSRF token to the model
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken != null) {
            model.addAttribute("_csrf", csrfToken);
        }
        return "userReservations";
    }
}
