package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.dto.response.VoyageResponse;
import com.solmaz.ticketplannermainservice.model.Ticket;
import com.solmaz.ticketplannermainservice.model.Voyage;
import com.solmaz.ticketplannermainservice.model.enums.TravelType;
import com.solmaz.ticketplannermainservice.model.enums.VoyageStatus;
import com.solmaz.ticketplannermainservice.repository.VoyageRepository;
import com.solmaz.ticketplannermainservice.util.DateTimeStringConvertor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VoyageServiceImplTest {

    @InjectMocks
    VoyageServiceImpl voyageService;

    @Mock
    VoyageRepository voyageRepository;

    @Mock
    ModelMapper modelMapper;


    @Test
    public void testCreate() {

        VoyageRequest voyageRequest = new VoyageRequest();
        voyageRequest.setOriginCity("ankara");
        voyageRequest.setDestinationCity("istanbul");
        voyageRequest.setPrice(100.0);
        voyageRequest.setVoyageDateTime("2023-01-01 21:00");
        voyageRequest.setTravelType(TravelType.BUS);

        Voyage voyage = new Voyage();
        voyage.setOriginCity("ankara");
        voyage.setDestinationCity("istanbul");
        voyage.setPrice(100.0);
        voyage.setVoyageDateTime(LocalDateTime.parse("2023-01-01T21:00:00"));
        voyage.setTravelType(TravelType.BUS);

        VoyageResponse voyageResponse = new VoyageResponse();
        voyageResponse.setOriginCity("ankara");
        voyageResponse.setDestinationCity("istanbul");
        voyageResponse.setPrice(100.0);
        voyageResponse.setVoyageDateTime("2023-01-01 21:00");

        voyage.setTravelType(TravelType.BUS);
        ModelMapper modelMapper = mock(ModelMapper.class);
        VoyageRepository voyageRepository = mock(VoyageRepository.class);

        when(modelMapper.map(voyageRequest, Voyage.class)).thenReturn(voyage);
        when(modelMapper.map(voyage, VoyageResponse.class)).thenReturn(voyageResponse);

        VoyageServiceImpl voyageService = new VoyageServiceImpl(voyageRepository, modelMapper);

        VoyageResponse result = voyageService.createVoyage(voyageRequest);

        verify(modelMapper).map(voyageRequest, Voyage.class);
        verify(voyageRepository).save(voyage);
        assertEquals(result, voyageResponse);
    }


    @Test
    public void deactivateVoyage_validId() {

        Integer id = 1;
        String originCity = "ankara";
        Voyage voyage = new Voyage();
        voyage.setId(id);
        voyage.setOriginCity(originCity);
        voyage.setVoyageStatus(VoyageStatus.ACTIVE);
        when(voyageRepository.findById(id)).thenReturn(Optional.of(voyage));
        voyageService.deactivateVoyage(1);

        assertEquals(VoyageStatus.PASSIVE, voyage.getVoyageStatus());
    }

    @Test
    public void deleteVoyage_validId() {

        Integer id = 1;
        Voyage voyage = new Voyage();
        voyage.setOriginCity("ankara");
        voyage.setId(id);

        VoyageResponse voyageResponse = new VoyageResponse();
        voyageResponse.setOriginCity("ankara");

        when(voyageRepository.findById(id)).thenReturn(Optional.of(voyage));
        when(modelMapper.map(voyage, VoyageResponse.class)).thenReturn(voyageResponse);

        VoyageResponse voyageResponse1 = voyageService.deleteVoyage(1);

        assertEquals(voyageResponse1.getOriginCity(), voyage.getOriginCity());
    }

    @Test
    public void getTotalTicketNumbersSold_validId_returnsCorrectNumber() {

        Integer id = 1;
        Voyage voyage = new Voyage();
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket());
        ticketList.add(new Ticket());
        voyage.setTicketList(ticketList);
        when(voyageRepository.findById(id)).thenReturn(Optional.of(voyage));

        int totalTicketNumbersSold = voyageService.getTotalTicketNumbersSold(id);

        assertEquals(2, totalTicketNumbersSold);
    }

    @Test
    public void getVoyagesByCityTravelTypeDateTime_validInput_returnsCorrectList() {
        String originCity = "malatya";
        String destinationCity = "manisa";
        TravelType travelType = TravelType.BUS;
        String voyageDateTime = "2022-01-01";

        Voyage voyage1 = new Voyage();
        voyage1.setOriginCity(originCity);
        voyage1.setDestinationCity(destinationCity);
        voyage1.setTravelType(travelType);
        voyage1.setVoyageDateTime(DateTimeStringConvertor.convertStringToLocalDateTime(voyageDateTime));
        Voyage voyage2 = new Voyage();
        voyage2.setOriginCity(originCity);
        voyage2.setDestinationCity(destinationCity);
        voyage2.setTravelType(travelType);
        voyage2.setVoyageDateTime(DateTimeStringConvertor.convertStringToLocalDateTime(voyageDateTime));

        List<Voyage> voyageList = new ArrayList<>();
        voyageList.add(voyage1);
        voyageList.add(voyage2);
        when(voyageRepository.filterVoyagesByCityTravelTypeDateTime(originCity, destinationCity, "BUS",
                DateTimeStringConvertor.convertStringToLocalDateTime(voyageDateTime))).thenReturn(voyageList);
        VoyageResponse voyageResponse1 = new VoyageResponse();
        voyageResponse1.setOriginCity(originCity);
        voyageResponse1.setDestinationCity(destinationCity);
        voyageResponse1.setTravelType(travelType);
        voyageResponse1.setVoyageDateTime(voyageDateTime);
        VoyageResponse voyageResponse2 = new VoyageResponse();
        voyageResponse2.setOriginCity(originCity);
        voyageResponse2.setDestinationCity(destinationCity);
        voyageResponse2.setTravelType(travelType);
        voyageResponse2.setVoyageDateTime(voyageDateTime);

        List<VoyageResponse> expectedVoyageResponseList = new ArrayList<>();
        expectedVoyageResponseList.add(voyageResponse1);
        expectedVoyageResponseList.add(voyageResponse2);
        when(modelMapper.map(voyage1, VoyageResponse.class)).thenReturn(voyageResponse1);
        when(modelMapper.map(voyage2, VoyageResponse.class)).thenReturn(voyageResponse2);

        List<VoyageResponse> actualVoyageResponseList = voyageService
                .getVoyagesByCityTravelTypeDateTime(originCity, destinationCity, "BUS", voyageDateTime);

        assertEquals(expectedVoyageResponseList, actualVoyageResponseList);
    }


}
