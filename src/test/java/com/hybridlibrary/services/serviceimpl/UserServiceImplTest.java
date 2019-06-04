package com.hybridlibrary.services.serviceimpl;

import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.exceptions.NotFoundException;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.UserRepository;
import com.hybridlibrary.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    private ConversionService conversionServiceMock;

    private UserRepository userRepositoryMock;



    @Before
    public void setUp() {
        conversionServiceMock = mock(ConversionService.class);
        userRepositoryMock = mock(UserRepository.class);
        userService = new UserServiceImpl(conversionServiceMock, userRepositoryMock);
    }

    @Test
    public void findAll() {
        List<User> users = new ArrayList<>();
        User user = User.builder()
                .id(1L)
                .firstName("Nicolais")
                .lastName("Kiraly")
                .email("nkiraly0@pcworld.com")
                .username("nkiraly0")
                .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                .role("ROLE_ADMIN")
                .build();
        User user1 = User.builder()
                .id(2L)
                .firstName("Cindra")
                .lastName("Besant")
                .email("cbesant1@army.mil")
                .username("cbesant1")
                .password("$2a$10$6yOYfJPCpucGIOI9R4/vhuoSo8pwT855Qu.4q7wmcdsSEiWIgXMv2")
                .role("ROLE_USER")
                .build();
        
        users.add(user);
        users.add(user1);
        
        when(userRepositoryMock.findAll()).thenReturn(users);
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(UserDto.builder()
                .id(1L)
                .firstName("Nicolais")
                .lastName("Kiraly")
                .email("nkiraly0@pcworld.com")
                .username("nkiraly0")
                .role("ROLE_ADMIN")
                .build())
                .thenReturn(UserDto.builder()
                        .id(2L)
                        .firstName("Cindra")
                        .lastName("Besant")
                        .email("cbesant1@army.mil")
                        .username("cbesant1")
                        .role("ROLE_USER")
                        .build());

        List<UserDto> userList = userService.findAll();

        assertEquals(users.size(), userList.size());

        verify(userRepositoryMock).findAll();
        verify(conversionServiceMock).convert(user, UserDto.class);
        verify(conversionServiceMock).convert(user1, UserDto.class);

        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void findAllNotFoundException() {

        List<User> list = new ArrayList<>();
        when(userRepositoryMock.findAll()).thenReturn(list);
        userService.findAll();

    }

    @Test
    public void getOne() {
    }

    @Test
    public void getByUsername() {
    }

    @Test
    public void findByUsername() {
    }

    @Test
    public void update() {
    }

    @Test
    public void create() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void existById() {
    }
}