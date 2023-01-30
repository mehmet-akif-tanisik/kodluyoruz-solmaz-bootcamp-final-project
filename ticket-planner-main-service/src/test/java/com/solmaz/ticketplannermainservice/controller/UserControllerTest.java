package com.solmaz.ticketplannermainservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.model.enums.Gender;
import com.solmaz.ticketplannermainservice.model.enums.PassengerType;
import com.solmaz.ticketplannermainservice.service.impl.UserServiceImpl;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    public void createUserAPI() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders
                        .post("/api/users")
                        .content(asJsonString(new UserRequest("Mehmet","Akif",
                                "Tanisik","password123","mail@mail.com",
                                "05467653423", Gender.MALE, PassengerType.CORPORATE)))
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