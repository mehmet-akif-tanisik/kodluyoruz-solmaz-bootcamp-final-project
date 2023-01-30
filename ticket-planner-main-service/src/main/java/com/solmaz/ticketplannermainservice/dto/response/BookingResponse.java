package com.solmaz.ticketplannermainservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class BookingResponse {

    private Integer id;
    private String passengerTelephoneNumber;
    private List<TicketResponse> ticketResponseList;
    private LocalDateTime creationDateTime;
    private double bookingTotalPrice;

    public BookingResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassengerTelephoneNumber() {
        return passengerTelephoneNumber;
    }

    public void setPassengerTelephoneNumber(String passengerTelephoneNumber) {
        this.passengerTelephoneNumber = passengerTelephoneNumber;
    }

    public List<TicketResponse> getTicketResponseList() {
        return ticketResponseList;
    }

    public void setTicketResponseList(List<TicketResponse> ticketResponseList) {
        this.ticketResponseList = ticketResponseList;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public double getBookingTotalPrice() {
        return bookingTotalPrice;
    }

    public void setBookingTotalPrice(double bookingTotalPrice) {
        this.bookingTotalPrice = bookingTotalPrice;
    }
}
