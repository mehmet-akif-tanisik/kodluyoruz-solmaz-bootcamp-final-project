package com.solmaz.ticketplannermainservice.dto.request;

import com.solmaz.ticketplannermainservice.model.enums.Gender;

public class TicketRequest {

    private String citizenshipNumber;
    private String passengerFirstName;
    private String passengerMiddleName;
    private String passengerLastName;
    private Gender gender;

    public TicketRequest() {
    }

    public TicketRequest(String citizenshipNumber, String passengerFirstName,
                         String passengerMiddleName, String passengerLastName,
                         Gender gender) {
        this.citizenshipNumber = citizenshipNumber;
        this.passengerFirstName = passengerFirstName;
        this.passengerMiddleName = passengerMiddleName;
        this.passengerLastName = passengerLastName;
        this.gender = gender;
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
}
