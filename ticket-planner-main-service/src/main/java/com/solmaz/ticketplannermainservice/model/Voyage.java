package com.solmaz.ticketplannermainservice.model;


import com.solmaz.ticketplannermainservice.model.enums.TravelType;
import com.solmaz.ticketplannermainservice.model.enums.VoyageStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "voyages")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "origin_city", length = 20)
    private String originCity;

    @Column(name = "destination_city", length = 20)
    private String destinationCity;

    @Column(name = "voyage_date_time")
    private LocalDateTime voyageDateTime;

    @Column(name = "available_seat")
    private int availableSeat;

    @Column(name = "travel_type")
    @Enumerated(EnumType.STRING)
    private TravelType travelType;

    @OneToMany(mappedBy = "voyage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ticket> ticketList;

    @Column(name = "voyage_status")
    @Enumerated(EnumType.STRING)
    private VoyageStatus voyageStatus;

    @Column(name = "price")
    private double price;

    public Voyage() {
        setVoyageStatus(VoyageStatus.ACTIVE);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDateTime getVoyageDateTime() {
        return voyageDateTime;
    }

    public void setVoyageDateTime(LocalDateTime voyageDateTime) {
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

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public VoyageStatus getVoyageStatus() {
        return voyageStatus;
    }

    public void setVoyageStatus(VoyageStatus voyageStatus) {
        this.voyageStatus = voyageStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
