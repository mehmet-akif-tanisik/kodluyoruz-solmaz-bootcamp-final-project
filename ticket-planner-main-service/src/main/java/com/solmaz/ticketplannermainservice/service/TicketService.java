package com.solmaz.ticketplannermainservice.service;

import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse createTicket(Integer voyageId, TicketRequest ticketRequest);

    TicketResponse updateTicket(Integer id, TicketRequest ticketRequest);

    TicketResponse getTicketById(Integer id);

    TicketResponse deleteTicketById(Integer id);

    List<TicketResponse> getAllByTelephoneNumber(String telephoneNumber);

}
