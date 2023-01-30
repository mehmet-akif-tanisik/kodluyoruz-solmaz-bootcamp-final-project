package com.solmaz.ticketplannermainservice.controller.advice;

import com.solmaz.ticketplannermainservice.exception.IndexOutOfBoundsException;
import com.solmaz.ticketplannermainservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingControllerAdvices {

    @ResponseBody
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookingNotFoundHandler(BookingNotFoundException exception) {
        return exception.getMessage();
    }


    @ResponseBody
    @ExceptionHandler(BookingMaleGenderLimitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookingMaleGenderLimitHandler(BookingMaleGenderLimitException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmptyTicketListException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String emptyTicketListHandler(EmptyTicketListException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TicketSaleLimitException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ticketSaleLimitHandler(TicketSaleLimitException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String indexOutOfBoundsHandler(IndexOutOfBoundsException ex) {
        return ex.getMessage();
    }

}
