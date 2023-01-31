package com.solmaz.ticketplannermainservice;

import com.solmaz.ticketplannermainservice.dto.request.NotificationRequest;
import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.dto.response.UserResponse;
import com.solmaz.ticketplannermainservice.exception.RoleNotFoundException;
import com.solmaz.ticketplannermainservice.model.user.Role;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ticket Planner API", version = "1.0", description = "Ticket Planner App - Kodluyoruz & Solmaz"))
//@SecurityScheme(name = "ticketplanner-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class TicketPlannerMainServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketPlannerMainServiceApplication.class, args);
	}

}







