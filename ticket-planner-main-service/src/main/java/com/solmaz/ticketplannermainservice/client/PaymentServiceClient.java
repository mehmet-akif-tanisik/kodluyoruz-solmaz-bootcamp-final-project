package com.solmaz.ticketplannermainservice.client;

import com.solmaz.ticketplannermainservice.client.model.Invoice;
import com.solmaz.ticketplannermainservice.dto.request.NotificationRequest;
import com.solmaz.ticketplannermainservice.exception.BookingNotFoundException;
import com.solmaz.ticketplannermainservice.model.Booking;
import com.solmaz.ticketplannermainservice.repository.BookingRepository;
import com.solmaz.ticketplannermainservice.service.impl.BookingServiceImpl;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentServiceClient {

    private final String PAYMENT_URL = "http://localhost:8095/payment";

    private final BookingRepository bookingRepository;
    private final BookingServiceImpl bookingService;
    private final RabbitTemplate rabbitTemplate;

    public PaymentServiceClient(BookingRepository bookingRepository,
                                BookingServiceImpl bookingService, RabbitTemplate rabbitTemplate) {
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Invoice payment(Integer bookingId, String paymentType) {

        Booking foundBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found."));

        Invoice invoice = bookingToInvoiceConvertor(foundBooking, paymentType);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Invoice> paymentRequest = new HttpEntity<>(invoice);

        var response = restTemplate.postForObject(PAYMENT_URL, paymentRequest, Invoice.class);

        bookingService.changeBookingPaymentStatus(bookingId);

        rabbitTemplate.convertAndSend("notification",
                new NotificationRequest("Thank you for your booking. Booking ID: " +
                        bookingId + response.getTelephoneNumber(), "SMS", response.getTelephoneNumber()));

        return response;
    }

    private Invoice bookingToInvoiceConvertor(Booking booking, String paymentType) {

        Invoice invoice = new Invoice();
        invoice.setBookingId(booking.getId());
        invoice.setEmail(booking.getPassenger().getEmail());
        invoice.setFirstName(booking.getPassenger().getFirstName());
        invoice.setMiddleName(booking.getPassenger().getMiddleName());
        invoice.setLastName(booking.getPassenger().getLastName());
        invoice.setTelephoneNumber(booking.getPassenger().getTelephoneNumber());
        invoice.setPaymentAmount(booking.getBookingTotalPrice());
        invoice.setPaymentType(paymentType);

        return invoice;
    }
}
