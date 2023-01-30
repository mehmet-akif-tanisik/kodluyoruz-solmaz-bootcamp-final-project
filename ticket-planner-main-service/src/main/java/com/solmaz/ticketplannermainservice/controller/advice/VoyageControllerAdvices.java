package com.solmaz.ticketplannermainservice.controller.advice;


import com.solmaz.ticketplannermainservice.exception.VoyageAvaliableSeatException;
import com.solmaz.ticketplannermainservice.exception.VoyageDateTimeException;
import com.solmaz.ticketplannermainservice.exception.VoyageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VoyageControllerAdvices {

    @ResponseBody
    @ExceptionHandler(VoyageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String voyageNotFoundHandler(VoyageNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(VoyageAvaliableSeatException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String voyageAvaliableSeatHandler(VoyageAvaliableSeatException exception){
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(VoyageDateTimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String voyageDateTimeHandler(VoyageDateTimeException exception)
    {
        return "Incorrect Voyage Date or Time.";
    }

}
