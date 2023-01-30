package com.solmaz.ticketplannermainservice.dto.request;

public class NotificationRequest {
    private String message;
    private String type;
    private String contact; //Username,TelNo,Mail

    public NotificationRequest() {
    }

    public NotificationRequest(String message, String type, String contact) {
        this.message = message;
        this.type = type;
        this.contact = contact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String toString() {
        return "Type: " + type + "\n" +
                "Contact: " + contact + "\n" +
                "Message: " + message + "\n";
    }
}
