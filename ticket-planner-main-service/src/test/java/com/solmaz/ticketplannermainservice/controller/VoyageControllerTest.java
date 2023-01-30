package com.solmaz.ticketplannermainservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solmaz.ticketplannermainservice.dto.request.VoyageRequest;
import com.solmaz.ticketplannermainservice.model.enums.TravelType;
import com.solmaz.ticketplannermainservice.service.impl.VoyageServiceImpl;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(VoyageController.class)
class VoyageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VoyageServiceImpl voyageService;

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void createVoyageAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/voyages")
                        .content(asJsonString(new VoyageRequest("malatya",
                                "tekirdag", "2022-02-22 22:00", TravelType.BUS,120)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void deactivateVoyageAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .put("/api/voyages/deactivate/{id}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void deleteVoyageAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("/api/voyages/delete/{id}", 1) )
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin",password = "admin",authorities = "ADMIN")
    public void getTotalTicketNumbersSoldAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/api/voyages/totalTicketsSold/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin",password = "admin",authorities = "ADMIN")
    public void getTotalEarninsgOfVoyageAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .get("/api/voyages/totalEarnings/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void getVoyagesAPI() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                        .get("/api/voyages/totalEarnings/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("originCity","manisa")
                        .param("destinationCity","malatya")
                        .param("travelType", String.valueOf(TravelType.PLANE))
                        .param("voyageDateTime","2022-02-22 22:00"))
                .andDo(print())
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