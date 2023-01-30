package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.response.BookingResponse;
import com.solmaz.ticketplannermainservice.dto.response.TicketResponse;
import com.solmaz.ticketplannermainservice.model.Booking;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.enums.PaymentStatus;
import com.solmaz.ticketplannermainservice.repository.BookingRepository;
import com.solmaz.ticketplannermainservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BookingServiceImplTest {
    @InjectMocks
    BookingServiceImpl bookingService;

    @Mock
    BookingRepository bookingRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    VoyageServiceImpl voyageService;

    @Mock
    ModelMapper modelMapper;

    @Test
    public void getBookingById() {
        Integer bookingId = 1;
        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setActive(true);
        booking.setId(bookingId);

        Ticket ticket = new Ticket();
        ticket.setId(1);

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        booking.setTicketList(ticketList);
        bookingRepository.save(booking);

        when(bookingRepository.findById(eq(bookingId))).thenReturn(Optional.of(booking));
        when(modelMapper.map(eq(booking), eq(BookingResponse.class))).thenReturn(new BookingResponse());
        when(modelMapper.map(eq(ticket), eq(TicketResponse.class))).thenReturn(new TicketResponse());

        BookingResponse bookingResponse = bookingService.getBookingById(bookingId);

        assertNotNull(bookingResponse);
        verify(bookingRepository).findById(eq(bookingId));
        verify(modelMapper).map(eq(booking), eq(BookingResponse.class));
        verify(modelMapper).map(eq(ticket), eq(TicketResponse.class));
    }


    @Test
    public void createBookingAPI() throws Exception {
    }

    @Test
    public void changeBookingPaymentStatus_shouldChangePaymentStatusToPaid() {
        Booking booking = new Booking();
        booking.setPaymentStatus(PaymentStatus.PENDING);
        when(bookingRepository.findById(anyInt())).thenReturn(java.util.Optional.of(booking));

        bookingService.changeBookingPaymentStatus(1);

        verify(bookingRepository, times(1)).findById(1);
        verify(bookingRepository, times(1)).save(booking);
        assertEquals(PaymentStatus.PAID, booking.getPaymentStatus());
    }
}