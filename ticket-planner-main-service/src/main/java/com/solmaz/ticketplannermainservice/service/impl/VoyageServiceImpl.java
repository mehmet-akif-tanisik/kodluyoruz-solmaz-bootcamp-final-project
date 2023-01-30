package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.dto.response.VoyageResponse;
import com.solmaz.ticketplannermainservice.exception.VoyageNotFoundException;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.Voyage;
import com.solmaz.ticketplannermainservice.model.enums.TravelType;
import com.solmaz.ticketplannermainservice.model.enums.VoyageStatus;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import com.solmaz.ticketplannermainservice.service.VoyageService;
import com.solmaz.ticketplannermainservice.util.Constants;
import com.solmaz.ticketplannermainservice.util.DateTimeStringConvertor;
import com.solmaz.ticketplannermainservice.util.LoggerHandler;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Service
public class VoyageServiceImpl implements VoyageService {

    private final VoyageRepository voyageRepository;
    private final ModelMapper modelMapper;

    public VoyageServiceImpl(VoyageRepository voyageRepository, ModelMapper modelMapper) {
        this.voyageRepository = voyageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VoyageResponse createVoyage(VoyageRequest voyageRequest) {

        Voyage voyage = modelMapper.map(voyageRequest, Voyage.class);

        setAvaliableSeatForVoyage(voyage);
        voyageRepository.save(voyage);

        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageServiceImpl -->createVoyage()--> voyage has been saved to voyageRepository.");

        return modelMapper.map(voyage, VoyageResponse.class);
    }

    private static void setAvaliableSeatForVoyage(Voyage voyage) {
        if (voyage.getTravelType().equals(TravelType.BUS)){
            voyage.setAvailableSeat(Constants.MAX_AVAILABLE_SEATS_FOR_BUS);
        } else {
            voyage.setAvailableSeat(Constants.MAX_AVAILABLE_SEATS_FOR_PLANE);
        }
        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageServiceImpl -->setAvaliableSeatForVoyage()--> voyage available seat has been set to inital value.");
    }

    @Override
    public VoyageResponse deactivateVoyage(Integer id) {

       Voyage voyage = voyageRepository.findById(id)
               .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

       voyage.setVoyageStatus(VoyageStatus.PASSIVE);
       voyageRepository.save(voyage);

        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageServiceImpl -->deactivateVoyage()--> voyageStatus has been set to PASSIVE and saved to voyageRepository.");

       return modelMapper.map(voyage, VoyageResponse.class);
    }

    @Override
    public VoyageResponse deleteVoyage(Integer id) {

        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

        voyageRepository.deleteById(id);

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageServiceImpl -->deleteVoyage()--> voyage deleted by ID from voyageRepository.");

        return modelMapper.map(voyage, VoyageResponse.class);
    }

    @Override
    public int getTotalTicketNumbersSold(Integer id) {
        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageServiceImpl -->getTotalTicketNumbersSold()--> voyage's sold tickets number received from voyage.");

        return voyage.getTicketList().size();
    }

    @Override
    public double getTotalEarninsgOfVoyage(Integer id) {
        Voyage voyage = voyageRepository.findById(id)
                .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

        double price = 0;

        for(Ticket element : voyage.getTicketList()){
            price += element.getPrice();
        }

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageServiceImpl -->getTotalEarninsgOfVoyage()--> Total earnings of voyage calculated.");

        return price;
    }

    @Override
    public List<VoyageResponse> getVoyagesByCityTravelTypeDateTime(String originCity, String destinationCity, String travelType, String voyageDateTime) {

        if (Objects.isNull(voyageDateTime)){

            List<VoyageResponse> voyageResponseList = voyageRepository
                    .filterVoyagesByCityTravelTypeDateTime(originCity,destinationCity,travelType,
                    DateTimeStringConvertor.formatLocalDateTime(LocalDateTime.now()))
                    .stream().map(voyage -> modelMapper.map(voyage, VoyageResponse.class)).toList();

            return voyageResponseList;
        }

        List<Voyage> list = voyageRepository
                .filterVoyagesByCityTravelTypeDateTime(originCity,destinationCity,travelType,
                DateTimeStringConvertor.convertStringToLocalDateTime(voyageDateTime));

        LoggerHandler.getLogger().log(Level.INFO,
                "VoyageServiceImpl -->getVoyagesByCityTravelTypeDateTime()--> Filtered voyages received from voyageRepository.");

        return list.stream().map(voyage -> modelMapper.map(voyage, VoyageResponse.class)).toList();
    }



    public Voyage getExactVoyage(BookingRequest bookingRequest){

        var voyage = voyageRepository.findVoyageByOriginCityAndDestinationCityAndTravelTypeAndVoyageDateTime(
                bookingRequest.getBookingOriginCity(),
                bookingRequest.getBookingDestinationCity(),
                bookingRequest.getBookingTravelType(),
                DateTimeStringConvertor.convertStringToLocalDateTime(bookingRequest.getBookingVoyageDateTime()))
                .orElseThrow(() -> new VoyageNotFoundException("There is no such voyage. Check voyage id."));

        LoggerHandler.getLogger().log(Level.WARNING,
                "VoyageServiceImpl -->getExactVoyage()--> voyage received by bookingRequest information from voyageRepository.");

        return voyage;
    }
}
