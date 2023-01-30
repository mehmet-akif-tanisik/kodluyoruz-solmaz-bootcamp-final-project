package com.solmaz.ticketplannermainservice.service;

import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.dto.response.UserResponse;
import com.solmaz.ticketplannermainservice.model.user.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    List<UserResponse> getAll();

    User findByEmail(String email);

}
