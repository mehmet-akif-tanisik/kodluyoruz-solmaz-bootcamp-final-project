package com.solmaz.ticketplannermainservice.dto.request;

import com.solmaz.ticketplannermainservice.model.enums.TravelType;

public class VoyageRequest {

    private String originCity;
    private String destinationCity;
    private String voyageDateTime;
    private TravelType travelType;
    private double price;

    public VoyageRequest() {
    }

    public VoyageRequest(String originCity, String destinationCity, String voyageDateTime,
                         TravelType travelType, double price) {
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.voyageDateTime = voyageDateTime;
        this.travelType = travelType;
        this.price = price;
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
