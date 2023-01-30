package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.NotificationRequest;
import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.dto.response.UserResponse;
import com.solmaz.ticketplannermainservice.exception.RoleNotFoundException;
import com.solmaz.ticketplannermainservice.exception.UserNotFoundException;
import com.solmaz.ticketplannermainservice.model.user.Role;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.repository.RoleRepository;
import com.solmaz.ticketplannermainservice.repository.UserRepository;
import com.solmaz.ticketplannermainservice.service.UserService;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
                           RoleRepository roleRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {

        User user = modelMapper.map(userRequest, User.class);

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found.")));

        LoggerHandler.getLogger().log(Level.INFO,
                "UserServiceImpl -->createUser()--> New User's Role has been set to ROLE(USER).");

        user.setRoles(roleSet);

        userRepository.save(user);

        LoggerHandler.getLogger().log(Level.INFO,
                "UserServiceImpl -->createUser()--> New User has been saved to userRepository");

        rabbitTemplate.convertAndSend("notification",
                new NotificationRequest("Thank you for creating a new Account in Ticket Planner Service." +
                        "Account created successfully with email address: " +
                        user.getEmail(), "MAIL", user.getEmail()));

        LoggerHandler.getLogger().log(Level.INFO,
                "UserServiceImpl -->createUser()--> Email Notification has been sent to NotificationService.");

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAll() {

        List<User> userList = userRepository.findAll();

        return userList.stream().map(user -> modelMapper.map(user, UserResponse.class)).toList();
    }

    @Override
    public User findByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return user;
    }
}
