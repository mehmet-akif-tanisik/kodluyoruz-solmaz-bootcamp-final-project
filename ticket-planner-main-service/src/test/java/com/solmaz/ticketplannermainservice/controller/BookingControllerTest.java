package com.solmaz.ticketplannermainservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solmaz.ticketplannermainservice.client.PaymentServiceClient;
import com.solmaz.ticketplannermainservice.dto.request.BookingRequest;
import com.solmaz.ticketplannermainservice.dto.request.PaymentRequest;
import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.TravelType;
import com.solmaz.ticketplannermainservice.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookingServiceImpl bookingService;

    @MockBean
    PaymentServiceClient paymentServiceClient;

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void createBookingAPI() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/booking")
                        .content(asJsonString(new BookingRequest("mail@mail.com",
                                List.of(new TicketRequest("14912345", "Mehmet",
                                        "Akif", "Tanisik", Gender.MALE)),
                                "ankara", "izmir",
                                "2023-10-17 19:00", TravelType.PLANE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void getBookingByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/booking/{bookingId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void paymentAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/booking/payment")
                        .content(asJsonString(new PaymentRequest(1, "CASH")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}