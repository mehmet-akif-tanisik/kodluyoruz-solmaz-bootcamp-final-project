package com.solmaz.ticketplannermainservice.dto.request;

import com.solmaz.ticketplannermainservice.model.enums.TravelType;

import java.util.List;

public class BookingRequest {
    private String bookingPassengerEmail;
    private List<TicketRequest> bookingTicketRequestList;
    private String bookingOriginCity;
    private String bookingDestinationCity;
    private String bookingVoyageDateTime;
    private TravelType bookingTravelType;

    public BookingRequest() {
    }

    public BookingRequest(String bookingPassengerEmail, List<TicketRequest> bookingTicketRequestList,
                          String bookingOriginCity, String bookingDestinationCity,
                          String bookingVoyageDateTime, TravelType bookingTravelType) {
        this.bookingPassengerEmail = bookingPassengerEmail;
        this.bookingTicketRequestList = bookingTicketRequestList;
        this.bookingOriginCity = bookingOriginCity;
        this.bookingDestinationCity = bookingDestinationCity;
        this.bookingVoyageDateTime = bookingVoyageDateTime;
        this.bookingTravelType = bookingTravelType;
    }

    public String getBookingPassengerEmail() {
        return bookingPassengerEmail;
    }

    public void setBookingPassengerEmail(String bookingPassengerEmail) {
        this.bookingPassengerEmail = bookingPassengerEmail;
    }

    public List<TicketRequest> getBookingTicketRequestList() {
        return bookingTicketRequestList;
    }

    public void setBookingTicketRequestList(List<TicketRequest> bookingTicketRequestList) {
        this.bookingTicketRequestList = bookingTicketRequestList;
    }

    public String getBookingOriginCity() {
        return bookingOriginCity;
    }

    public void setBookingOriginCity(String bookingOriginCity) {
        this.bookingOriginCity = bookingOriginCity;
    }

    public String getBookingDestinationCity() {
        return bookingDestinationCity;
    }

    public void setBookingDestinationCity(String bookingDestinationCity) {
        this.bookingDestinationCity = bookingDestinationCity;
    }

    public String getBookingVoyageDateTime() {
        return bookingVoyageDateTime;
    }

    public void setBookingVoyageDateTime(String bookingVoyageDateTime) {
        this.bookingVoyageDateTime = bookingVoyageDateTime;
    }

    public TravelType getBookingTravelType() {
        return bookingTravelType;
    }

    public void setBookingTravelType(TravelType bookingTravelType) {
        this.bookingTravelType = bookingTravelType;
    }
}
