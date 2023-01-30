package com.solmaz.ticketplannermainservice.controller;

import com.solmaz.ticketplannermainservice.dto.request.NotificationRequest;
import com.solmaz.ticketplannermainservice.exception.RoleNotFoundException;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.PassengerType;
import com.solmaz.ticketplannermainservice.model.user.Role;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.repository.RoleRepository;
import com.solmaz.ticketplannermainservice.service.impl.CustomUserDetailsService;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

@Controller
public class HomeController {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RabbitTemplate rabbitTemplate;

    public HomeController(CustomUserDetailsService customUserDetailsService,
                          PasswordEncoder passwordEncoder, RoleRepository roleRepository,
                          RabbitTemplate rabbitTemplate) {
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui/index.html";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session) {

        session.setAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = {
            MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public void addUser(@RequestParam Map<String, String> body) {
        User user = new User();

        user.setEmail(body.get("email"));
        user.setPassword(passwordEncoder.encode(body.get("password")));

        user.setFirstName(body.get("firstName"));
        user.setMiddleName(body.get("middleName"));
        user.setLastName(body.get("lastName"));
        user.setGender(Gender.valueOf(body.get("gender")));
        user.setTelephoneNumber(body.get("telephoneNumber"));
        user.setPassengerType(PassengerType.valueOf(body.get("passengerType")));


        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName("USER").orElseThrow(() -> new RoleNotFoundException("Role not found")));
        user.setRoles(roles);

        customUserDetailsService.createUser(user);
        rabbitTemplate.convertAndSend("notification",
                new NotificationRequest("Thank you for creating a new Account in Ticket Planner Service." +
                        "Account created successfully with email address: " + user.getEmail(), "MAIL", user.getEmail()));

        LoggerHandler.getLogger().log(Level.INFO,
                "HomeController --> addUser()--> Email Notification has been sent to NotificationService.");
    }

    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error = "";

        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }
}
