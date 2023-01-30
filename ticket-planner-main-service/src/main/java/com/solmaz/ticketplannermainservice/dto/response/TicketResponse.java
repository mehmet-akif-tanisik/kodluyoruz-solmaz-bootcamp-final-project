package com.solmaz.ticketplannermainservice.dto.response;

import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.TravelType;

import java.time.LocalDateTime;

public class TicketResponse {
    private String citizenshipNumber;
    private String passengerFirstName;
    private String passengerMiddleName;
    private String passengerLastName;
    private Gender gender;
    private double price;
    private String voyageOriginCity;
    private String voyageDestinationCity;
    private LocalDateTime voyageDateTime;
    private TravelType voyageTravelType;

    public TicketResponse() {
    }

    public String getCitizenshipNumber() {
        return citizenshipNumber;
    }

    public void setCitizenshipNumber(String citizenshipNumber) {
        this.citizenshipNumber = citizenshipNumber;
    }

    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }

    public String getPassengerMiddleName() {
        return passengerMiddleName;
    }

    public void setPassengerMiddleName(String passengerMiddleName) {
        this.passengerMiddleName = passengerMiddleName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVoyageOriginCity() {
        return voyageOriginCity;
    }

    public void setVoyageOriginCity(String voyageOriginCity) {
        this.voyageOriginCity = voyageOriginCity;
    }

    public String getVoyageDestinationCity() {
        return voyageDestinationCity;
    }

    public void setVoyageDestinationCity(String voyageDestinationCity) {
        this.voyageDestinationCity = voyageDestinationCity;
    }

    public LocalDateTime getVoyageDateTime() {
        return voyageDateTime;
    }

    public void setVoyageDateTime(LocalDateTime voyageDateTime) {
        this.voyageDateTime = voyageDateTime;
    }

    public TravelType getVoyageTravelType() {
        return voyageTravelType;
    }

    public void setVoyageTravelType(TravelType voyageTravelType) {
        this.voyageTravelType = voyageTravelType;
    }
}
