package com.solmaz.ticketplannermainservice.dto.request;

import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.PassengerType;

public class UserRequest {

    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String email;
    private String telephoneNumber;
    private Gender gender;
    private PassengerType passengerType;

    public UserRequest() {
    }

    public UserRequest(String firstName, String middleName, String lastName, String password,
                       String email, String telephoneNumber, Gender gender, PassengerType passengerType) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.gender = gender;
        this.passengerType = passengerType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public PassengerType getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(PassengerType passengerType) {
        this.passengerType = passengerType;
    }
}
