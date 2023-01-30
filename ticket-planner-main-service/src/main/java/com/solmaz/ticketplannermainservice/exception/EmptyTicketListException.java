package com.solmaz.ticketplannermainservice.exception;

public class EmptyTicketListException extends RuntimeException {

    public EmptyTicketListException(String message) {
        super(message);
    }
}
