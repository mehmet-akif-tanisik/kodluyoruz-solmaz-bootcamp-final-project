package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;
import com.solmaz.ticketplannermainservice.exception.TicketNotFoundException;
import com.solmaz.ticketplannermainservice.exception.UserNotFoundException;
import com.solmaz.ticketplannermainservice.exception.VoyageNotFoundException;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.Voyage;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.repository.TicketRepository;
import com.solmaz.ticketplannermainservice.repository.UserRepository;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import com.solmaz.ticketplannermainservice.service.TicketService;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final VoyageRepository voyageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TicketServiceImpl(TicketRepository ticketRepository, VoyageRepository voyageRepository,
                             UserRepository userRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.voyageRepository = voyageRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TicketResponse createTicket(Integer voyageId, TicketRequest ticketRequest) {

        Voyage voyage = voyageRepository.findById(voyageId)
                .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketServiceImpl -->createTicket()--> voyage received by ID from voyageRepository.");

        Ticket ticket = modelMapper.map(ticketRequest, Ticket.class);
        ticket.setPrice(voyage.getPrice());
        ticket.setVoyage(voyage);

        ticketRepository.save(ticket);

        return modelMapper.map(ticket, TicketResponse.class);
    }

    @Override
    public TicketResponse updateTicket(Integer id, TicketRequest ticketRequest) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("There is no such ticket. Check ticket id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketServiceImpl -->updateTicket()--> ticket received by ID from ticketRepository.");

        ticket.setCitizenshipNumber(ticket.getCitizenshipNumber());
        ticket.setPassengerFirstName(ticketRequest.getPassengerFirstName());
        ticket.setPassengerMiddleName(ticketRequest.getPassengerMiddleName());
        ticket.setPassengerLastName(ticketRequest.getPassengerLastName());
        ticket.setGender(ticketRequest.getGender());

        ticketRepository.save(ticket);

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketServiceImpl -->updateTicket()--> ticket updated and saved to ticketRepository.");

        return modelMapper.map(ticket, TicketResponse.class);
    }

    @Override
    public TicketResponse getTicketById(Integer id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("There is no such ticket. Check ticket id."));

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketServiceImpl -->getTicketById()--> ticket received by ID from ticketRepository.");

        return modelMapper.map(ticket, TicketResponse.class);
    }

    @Override
    public TicketResponse deleteTicketById(Integer id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("There is no such ticket. Check ticket id."));

        ticketRepository.deleteById(id);

        LoggerHandler.getLogger().log(Level.WARNING,
                "TicketServiceImpl -->deleteTicketById()--> ticket deleted by ID from ticketRepository.");

        return modelMapper.map(ticket, TicketResponse.class);
    }

    @Override
    public List<TicketResponse> getAllByTelephoneNumber(String email) {

        User passenger = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found."));

        List<Ticket> ticketList = passenger.getBookingList()
                .stream().flatMap(booking -> booking.getTicketList().stream()).toList();

        LoggerHandler.getLogger().log(Level.INFO,
                "TicketServiceImpl -->getAllByTelephoneNumber()--> ticketList received from user's TicketList.");

        return ticketList.stream().map(ticket -> modelMapper.map(ticket, TicketResponse.class)).toList();
    }
}
