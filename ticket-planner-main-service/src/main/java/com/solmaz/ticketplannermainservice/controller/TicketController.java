package com.solmaz.ticketplannermainservice.controller;

import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;
import com.solmaz.ticketplannermainservice.service.impl.TicketServiceImpl;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/tickets")
@SecurityRequirement(name = "ticketplanner-api")
public class TicketController {
    private final TicketServiceImpl ticketService;

    public TicketController(TicketServiceImpl ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/{voyageId}")
    public TicketResponse createTicket(@PathVariable Integer voyageId, @RequestBody TicketRequest ticketRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->createTicket()--> voyage ID and TicketRequest has been sent to TicketService createTicket()");

        return ticketService.createTicket(voyageId, ticketRequest);
    }

    @PutMapping("{ticketId}")
    public TicketResponse updateTicket(@PathVariable Integer ticketId, @RequestBody TicketRequest ticketRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->updateTicket()--> ticket ID and TicketRequest has been sent to TicketService updateTicket()");

        return ticketService.updateTicket(ticketId, ticketRequest);
    }

    @GetMapping("/byId/{ticketId}")
    public TicketResponse getTicketById(@PathVariable Integer ticketId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->getTicketById()--> ticket ID has been sent to TicketService getTicketById()");

        return ticketService.getTicketById(ticketId);
    }

    @GetMapping("/byTelNo/{telephoneNumber}")
    public List<TicketResponse> getAllByTelephoneNumber(@PathVariable String telephoneNumber) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->getAllByTelephoneNumber()--> telephoneNumber has been sent to TicketService getAllByTelephoneNumber()");

        return ticketService.getAllByTelephoneNumber(telephoneNumber);
    }

    @DeleteMapping("/{ticketId}")
    public TicketResponse deleteTicketById(@PathVariable Integer ticketId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "TicketController -->getAllByTelephoneNumber()--> ticket ID has been sent to TicketService deleteTicketById()");

        return ticketService.deleteTicketById(ticketId);
    }
}
