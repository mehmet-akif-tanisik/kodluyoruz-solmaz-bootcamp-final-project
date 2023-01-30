package com.solmaz.ticketplannermainservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solmaz.ticketplannermainservice.dto.request.TicketRequest;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.service.impl.TicketServiceImpl;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TicketServiceImpl ticketService;

    @Test
    void createTicket() {
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void createVoyageAPI() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/tickets/{voyageId}", 1)
                        .content(asJsonString(new TicketRequest("14912345",
                                "Mehmet", "Akif", "Tanisik", Gender.MALE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void updateTicketAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .put("/api/tickets/{ticketId}", 2)
                        .content(asJsonString(new TicketRequest("14912345",
                                "Mehmet", "Akif", "Tanisik", Gender.MALE)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void getTicketByIdAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/tickets/byId/{ticketId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void getAllByTelephoneNumberAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/tickets/byTelNo/{telephoneNumber}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "user", authorities = {"USER", "ADMIN"})
    public void deleteTicketAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/tickets/{id}", 1))
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