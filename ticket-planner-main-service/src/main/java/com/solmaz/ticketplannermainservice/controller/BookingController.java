package com.solmaz.ticketplannermainservice.controller;

import com.solmaz.ticketplannermainservice.client.PaymentServiceClient;
import com.solmaz.ticketplannermainservice.client.model.Invoice;
import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.request.PaymentRequest;
import com.solmaz.ticketplannermainservice.dto.response.BookingResponse;
import com.solmaz.ticketplannermainservice.service.impl.BookingServiceImpl;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final BookingServiceImpl bookingService;

    private final PaymentServiceClient paymentServiceClient;

    public BookingController(BookingServiceImpl bookingService, PaymentServiceClient paymentServiceClient) {
        this.bookingService = bookingService;
        this.paymentServiceClient = paymentServiceClient;
    }

    @PostMapping
    public BookingResponse createBooking(@RequestBody BookingRequest bookingRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> createBooking()--> bookingRequest has been sent to BookingService createBooking()");

        return bookingService.createBooking(bookingRequest);
    }

    @GetMapping("/{bookingId}")
    public BookingResponse getBookingById(@PathVariable Integer bookingId) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> getBookingById()--> booking ID has been sent to BookingService getBookingById()");

        return bookingService.getBookingById(bookingId);
    }

    @PostMapping("/payment")
    public ResponseEntity<Invoice> payment(@RequestBody PaymentRequest paymentRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "BookingController --> getBookingById()--> paymentRequest has been sent to PaymentServiceClient payment()");

        return new ResponseEntity<>(paymentServiceClient.payment(paymentRequest.getBookingId(), paymentRequest.getPaymentType()), HttpStatus.OK);
    }

}
