package com.Hotel.hotel.service;

import com.Hotel.hotel.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByEmail(String email);
    void saveUser(User user);
}
