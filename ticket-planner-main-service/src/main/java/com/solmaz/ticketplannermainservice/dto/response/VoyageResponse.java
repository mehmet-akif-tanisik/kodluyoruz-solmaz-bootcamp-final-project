package com.solmaz.ticketplannermainservice.dto.response;

import com.solmaz.ticketplannermainservice.model.enums.TravelType;

public class VoyageResponse {

    private String originCity;
    private String destinationCity;
    private String voyageDateTime;
    private int availableSeat;
    private TravelType travelType;

    private double price;

    public VoyageResponse() {
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getVoyageDateTime() {
        return voyageDateTime;
    }

    public void setVoyageDateTime(String voyageDateTime) {
        this.voyageDateTime = voyageDateTime;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = availableSeat;
    }

    public TravelType getTravelType() {
        return travelType;
    }

    public void setTravelType(TravelType travelType) {
        this.travelType = travelType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
