package com.solmaz.ticketplannermainservice.service;

import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.dto.response.VoyageResponse;
import com.solmaz.ticketplannermainservice.model.Voyage;

import java.util.List;

public interface VoyageService {
    VoyageResponse createVoyage(VoyageRequest voyageRequest);

    VoyageResponse deactivateVoyage(Integer id);

    VoyageResponse deleteVoyage(Integer id);

    int getTotalTicketNumbersSold(Integer id);

    double getTotalEarninsgOfVoyage(Integer id);

    List<VoyageResponse> getVoyagesByCityTravelTypeDateTime(String originCity, String destinationCity, String travelType, String voyageDateTime);

    Voyage getExactVoyage(BookingRequest bookingRequest);

}
