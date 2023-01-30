package com.solmaz.ticketplannermainservice.exception;

public class TicketSaleLimitException extends RuntimeException {

    public TicketSaleLimitException(String message) {
        super(message);
    }
}
