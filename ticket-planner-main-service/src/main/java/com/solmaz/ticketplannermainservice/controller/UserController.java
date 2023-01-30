package com.solmaz.ticketplannermainservice.controller;

import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.dto.response.UserResponse;
import com.solmaz.ticketplannermainservice.service.impl.UserServiceImpl;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.RoleNotFoundException;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) throws RoleNotFoundException {
        LoggerHandler.getLogger().log(Level.INFO,
                "UserController -->createUser()--> userRequest has been sent to UserService createUser().");

        return userService.createUser(userRequest);
    }
}
