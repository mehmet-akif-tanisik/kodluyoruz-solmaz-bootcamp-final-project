package com.solmaz.ticketplannermainservice.dto.request;

public class PaymentRequest {

    private Integer bookingId;
    private String paymentType;

    public PaymentRequest(Integer bookingId, String paymentType) {
        this.bookingId = bookingId;
        this.paymentType = paymentType;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
