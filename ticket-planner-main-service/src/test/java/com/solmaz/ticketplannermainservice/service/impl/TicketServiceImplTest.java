package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.Voyage;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.repository.TicketRepository;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {


    @InjectMocks
    TicketServiceImpl ticketService;
    @Mock
    VoyageRepository voyageRepository;
    @Mock
    TicketRepository ticketRepository;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void testCreateAPI() {

        Voyage voyage = new Voyage();
        voyage.setId(1);
        voyage.setPrice(100.0);
        when(voyageRepository.findById(1)).thenReturn(Optional.of(voyage));
        TicketRequest request = new TicketRequest();
        request.setPassengerFirstName("Harkow Destin");
        request.setCitizenshipNumber("2233445566");
        request.setGender(Gender.MALE);
        request.setPassengerLastName("Destiny");
        when(modelMapper.map(request, Ticket.class)).thenReturn(new Ticket());
        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());
        when(modelMapper.map(any(Ticket.class), eq(TicketResponse.class))).thenReturn(new TicketResponse());

        TicketResponse response = ticketService.createTicket(1, request);

        assertNotNull(response);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testUpdate() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        TicketRequest request = new TicketRequest();
        request.setPassengerFirstName("Harkow Destin");
        request.setCitizenshipNumber("2233445566");
        request.setGender(Gender.MALE);
        request.setPassengerLastName("Destiny");
        when(ticketRepository.save(any(Ticket.class))).thenReturn(new Ticket());
        when(modelMapper.map(any(Ticket.class), eq(TicketResponse.class))).thenReturn(new TicketResponse());

        TicketResponse response = ticketService.updateTicket(1, request);

        assertNotNull(response);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void getTicketById() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket, TicketResponse.class)).thenReturn(new TicketResponse());

        TicketResponse response = ticketService.getTicketById(1);

        assertNotNull(response);
    }

    @Test
    public void deleteVoyage_validId() {
        Integer id = 1;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setPassengerFirstName("mehmet");

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setPassengerFirstName("mehmet");

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(modelMapper.map(ticket, TicketResponse.class)).thenReturn(ticketResponse);

        TicketResponse ticketResponse1 = ticketService.getTicketById(1);

        assertEquals(ticketResponse1.getPassengerFirstName(), ticket.getPassengerFirstName());
    }

}