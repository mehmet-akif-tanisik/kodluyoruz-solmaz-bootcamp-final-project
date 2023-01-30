package com.solmaz.ticketplannermainservice.service.impl;

import com.solmaz.ticketplannermainservice.dto.request.UserRequest;
import com.solmaz.ticketplannermainservice.dto.response.UserResponse;
import com.solmaz.ticketplannermainservice.model.user.Role;
import com.solmaz.ticketplannermainservice.model.user.User;
import com.solmaz.ticketplannermainservice.repository.RoleRepository;
import com.solmaz.ticketplannermainservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        User user = new User();
        UserResponse userResponse = new UserResponse();
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role();
        role.setRoleName("USER");
        roleSet.add(role);
        user.setRoles(roleSet);
        user.setEmail("test@email.com");

        doReturn(user).when(modelMapper).map(userRequest, User.class);
        doReturn(Optional.of(role)).when(roleRepository).findByRoleName("USER");
        doReturn(userResponse).when(modelMapper).map(user, UserResponse.class);
        UserResponse response = userService.createUser(userRequest);

        verify(userRepository).save(user);
        assertEquals(userResponse, response);
    }

    @Test
    public void testGetAll() {

        List<User> mockUserList = Arrays.asList(
                new User(),
                new User(),
                new User()
        );
        when(userRepository.findAll()).thenReturn(mockUserList);


        List<UserResponse> result = userService.getAll();

        assertEquals(mockUserList.size(), result.size());
        for (User user : mockUserList) {
            verify(modelMapper).map(user, UserResponse.class);
        }
    }

    @Test
    public void testFindByEmail() {

        String email = "test@email.com";
        User mockUser = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        User result = userService.findByEmail(email);

        assertSame(mockUser, result);
    }
}
