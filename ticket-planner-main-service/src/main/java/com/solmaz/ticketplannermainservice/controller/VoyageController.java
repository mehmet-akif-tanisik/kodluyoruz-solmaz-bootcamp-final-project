package com.solmaz.ticketplannermainservice.controller;

import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.dto.response.VoyageResponse;
import com.solmaz.ticketplannermainservice.service.impl.VoyageServiceImpl;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;

@RestController
@RequestMapping("/api/voyages")
@SecurityRequirement(name = "ticketplanner-api")
public class VoyageController {
    private final VoyageServiceImpl voyageService;

    public VoyageController(VoyageServiceImpl voyageService) {
        this.voyageService = voyageService;
    }

    @PostMapping
    public VoyageResponse createVoyage(@RequestBody VoyageRequest voyageRequest) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->createVoyage()--> voyageRequest has been sent to VoyageService createVoyage().");

        return voyageService.createVoyage(voyageRequest);
    }

    @PutMapping("/deactivate/{id}")
    public VoyageResponse deactivateVoyage(@PathVariable Integer id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->deactivateVoyage()--> voyage ID has been sent to VoyageService deactivateVoyage().");

        return voyageService.deactivateVoyage(id);
    }

    @DeleteMapping("/delete/{id}")
    public VoyageResponse deleteVoyage(@PathVariable Integer id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->deleteVoyage()--> voyage ID has been sent to VoyageService deleteVoyage().");

        return voyageService.deleteVoyage(id);
    }

    @GetMapping("/totalTicketsSold/{id}")
    public int getTotalTicketNumbersSold(@PathVariable Integer id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getTotalTicketNumbersSold()--> voyage ID has been sent to VoyageService getTotalTicketNumbersSold().");

        return voyageService.getTotalTicketNumbersSold(id);
    }

    @GetMapping("/totalEarnings/{id}")
    public double getTotalEarninsgOfVoyage(@PathVariable Integer id) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getTotalEarninsgOfVoyage()--> voyage ID has been sent to VoyageService getTotalEarninsgOfVoyage().");

        return voyageService.getTotalEarninsgOfVoyage(id);
    }

    @GetMapping
    public List<VoyageResponse> getVoyages(@RequestParam String originCity,
                                           @RequestParam String destinationCity,
                                           @RequestParam(required = false) String travelType,
                                           @RequestParam(required = false) String voyageDateTime) {
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageController -->getVoyages()--> originCity, destinationCity, travelType, voyageDateTime has been sent to VoyageService getVoyages().");

        return voyageService.getVoyagesByCityTravelTypeDateTime(originCity, destinationCity, travelType, voyageDateTime);
    }
}
