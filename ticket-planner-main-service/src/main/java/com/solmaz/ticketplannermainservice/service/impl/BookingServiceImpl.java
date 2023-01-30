package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.dto.response.BookingResponse;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;
import com.solmaz.ticketplannermainservice.exception.*;
import com.solmaz.ticketplannermainservice.model.Booking;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.Voyage;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.PassengerType;
import com.solmaz.ticketplannermainservice.model.enums.PaymentStatus;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.repository.BookingRepository;
import com.solmaz.ticketplannermainservice.repository.UserRepository;
import com.solmaz.ticketplannermainservice.service.BookingService;
import com.solmaz.ticketplannermainservice.util.Constants;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.logging.Level;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;
    private final VoyageServiceImpl voyageService;

    private final ModelMapper modelMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository,
                              VoyageServiceImpl voyageService, ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.voyageService = voyageService;
        this.modelMapper = modelMapper;
    }

    @Override
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {

        checkBookingTicketListSize(bookingRequest);
        Voyage requestedVoyage = voyageService.getExactVoyage(bookingRequest);

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingServiceImpl --> createBooking()--> voyage received by voyageService-->getExactVoyage().");

        checkVoyageHasAvaliableSeats(requestedVoyage, bookingRequest);

        User passenger = getBookingPassenger(bookingRequest);

        checkBookingConstraints(bookingRequest, passenger, requestedVoyage);

        Booking booking = new Booking();

        List<Ticket> ticketList = bookingRequest.getBookingTicketRequestList().stream()
                .map(ticketRequest -> modelMapper.map(ticketRequest, Ticket.class)).toList();

        processBooking(requestedVoyage, passenger, booking, ticketList);

        bookingRepository.save(booking);
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingServiceImpl -->createBooking()--> bookingRequest saved to bookingRepository as Booking.");

        BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);
        bookingResponse.setTicketResponseList(ticketList.stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class)).toList());

        return bookingResponse;
    }


    @Override
    public BookingResponse getBookingById(Integer bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found"));

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingServiceImpl --> getBookingById()--> booking received by ID from bookingRepository.");

        BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);

        bookingResponse.setTicketResponseList(booking.getTicketList().stream()
                .map(ticket -> modelMapper.map(ticket, TicketResponse.class)).toList());

        return bookingResponse;

    }

    @Override
    public void changeBookingPaymentStatus(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("booking not found"));

        booking.setPaymentStatus(PaymentStatus.PAID);

        LoggerHandler.getLogger().log(Level.INFO,
                "BookingServiceImpl -->changeBookingPaymentStatus()--> booking payment status has been changed.");

        bookingRepository.save(booking);
    }


    private static void checkBookingConstraints(BookingRequest bookingRequest, User passenger, Voyage voyage) {
        long userTicketCount = passenger.getBookingList().stream()
                .flatMap(booking -> booking.getTicketList()
                        .stream()
                        .filter(ticket -> ticket.getVoyage().getId().equals(passenger.getId()))).count();

        if (passenger.getPassengerType().equals(PassengerType.CORPORATE)) {
            if (bookingRequest.getBookingTicketRequestList().size() + userTicketCount > Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER) {

                LoggerHandler.getLogger().log(Level.WARNING,
                        "BookingServiceImpl -->checkBookingConstraints()-->" +
                                "bookingRequest checked for Ticket Limit Size(" +
                                Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER +
                                ") for CORPORATE passenger.");

                throw new TicketSaleLimitException("Corporate passenger can not book more than " +
                        Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_CORPORATE_PASSENGER +
                        " tickets for one booking.");
            }
        }

        if (passenger.getPassengerType().equals(PassengerType.INDIVIDUAL)) {
            if (bookingRequest.getBookingTicketRequestList().size() + userTicketCount > Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER) {

                LoggerHandler.getLogger().log(Level.WARNING,
                        "BookingServiceImpl -->checkBookingConstraints()-->" +
                                "bookingRequest checked for Ticket Limit Size(" +
                                Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                                ") for INDIVIDUAL passenger.");

                throw new TicketSaleLimitException("Individual passenger can not book more than "
                        + Constants.MAX_ALLOWED_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                        " tickets for one booking.");
            }
            int malePassengerInTicketCounter = 0;

            for (TicketRequest element : bookingRequest.getBookingTicketRequestList()) {
                if (element.getGender().equals(Gender.MALE)) {
                    malePassengerInTicketCounter++;
                }
                if (malePassengerInTicketCounter > Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER) {

                    LoggerHandler.getLogger().log(Level.WARNING, "BookingServiceImpl -->checkBookingConstraints()-->" +
                            "bookingRequest checked for Male Limit Size(" +
                            Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                            ") for INDIVIDUAL passenger.");

                    throw new TicketSaleLimitException("Individual passenger can not book for more than " +
                            Constants.MAX_ALLOWED_MALE_TICKETS_PER_BOOKING_FOR_INDIVIDUAL_PASSENGER +
                            " male passengers.");
                }
            }
        }
    }


    private void processBooking(Voyage requestedVoyage, User passenger, Booking booking, List<Ticket> ticketList) {

        ticketList.stream().forEach(ticket -> {
            ticket.setBooking(booking);
            ticket.setVoyage(requestedVoyage);
            ticket.setPrice(requestedVoyage.getPrice());
        });

        requestedVoyage.setAvailableSeat(requestedVoyage.getAvailableSeat() - ticketList.size());
        booking.setTicketList(ticketList);
        booking.setBookingTotalPrice();
        booking.setPassenger(passenger);
    }


    private static void checkBookingTicketListSize(BookingRequest bookingRequest) {
        if (bookingRequest.getBookingTicketRequestList() == null || !(bookingRequest.getBookingTicketRequestList().size() > 0)) {
            throw new EmptyTicketListException("Null or empty ticket list to create booking.");
        }
    }

    private User getBookingPassenger(BookingRequest bookingRequest) {

        User passenger = userRepository.findByEmail(bookingRequest.getBookingPassengerEmail())
                .orElseThrow(() -> new UserNotFoundException("There is no such Uer. Check user id."));

        return passenger;
    }

    private static void checkVoyageHasAvaliableSeats(Voyage voyage, BookingRequest bookingRequest) {

        if (voyage.getAvailableSeat() < bookingRequest.getBookingTicketRequestList().size()) {

            throw new VoyageAvaliableSeatException("Voyage has no avaliable seat for booking.");
        }
    }
}
