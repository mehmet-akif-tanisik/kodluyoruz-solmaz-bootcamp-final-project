package com.solmaz.ticketplannermainservice.service;

import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);

    BookingResponse getBookingById(Integer bookingId);

    void changeBookingPaymentStatus(Integer bookingId);

}
