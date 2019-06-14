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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

        verify(userRepositoryMock).findAll();
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_getOneUser() {
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();

        when(userRepositoryMock.existsById(user.getId())).thenReturn(true);
        when(userRepositoryMock.getOne(user.getId())).thenReturn(user);
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build());

        UserDto result = userService.getOne(1L);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getEmail(), result.getEmail());

        verify(userRepositoryMock).existsById(user.getId());
        verify(userRepositoryMock).getOne(user.getId());
        verify(conversionServiceMock).convert(user, UserDto.class);

        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }
    @Test(expected = NotFoundException.class)
    public void test_getOneUserNotFoundException() {

        when(userRepositoryMock.existsById(-100L)).thenReturn(false);

        userService.getOne(-100L);
        verify(userRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_getUserByUsername() {
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepositoryMock.findByUsernameContainingIgnoreCase(user.getUsername())).thenReturn(userList);
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build());

        List<UserDto> result = userService.getByUsername("user");

        assertEquals(userList.size(), result.size());

        verify(userRepositoryMock).findByUsernameContainingIgnoreCase(user.getUsername());
        verify(conversionServiceMock).convert(user, UserDto.class);

        verifyNoMoreInteractions(conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_getBookByAuthorNotFoundException() {
        List<User> list = new ArrayList<>();
        when(userRepositoryMock.findByUsernameContainingIgnoreCase("ghjkl")).thenReturn(list);
        userService.getByUsername("ghjkl");

        verify(userRepositoryMock).findByUsernameContainingIgnoreCase("ghjkl");
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_updateBook() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build();
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();
        when(conversionServiceMock.convert(userDto, User.class)).thenReturn(user);
        when(userRepositoryMock.existsById(user.getId())).thenReturn(true);
        when(userRepositoryMock.save(user)).thenReturn(User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build());
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(userDto);

        userService.update(userDto);

        verify(conversionServiceMock).convert(userDto, User.class);
        verify(conversionServiceMock).convert(user, UserDto.class);
        verify(conversionServiceMock).convert(userDto, User.class);
        verify(userRepositoryMock).existsById(user.getId());
        verify(userRepositoryMock).save(user);
        verify(conversionServiceMock).convert(user, UserDto.class);


        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_updateUserNotFoundException() {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build();
        when(conversionServiceMock.convert(userDto, User.class)).thenReturn(User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build());
        when(userRepositoryMock.existsById(-100L)).thenReturn(false);
        userService.update(userDto);

        verify(conversionServiceMock).convert(userDto, User.class);
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test(expected = NotFoundException.class)
    public void test_deleteUserNotFoundException() {
        when(userRepositoryMock.existsById(-100L)).thenReturn(false);
        userService.delete(-100L);

        verify(userRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_deleteUser() {
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build();

        when(userRepositoryMock.existsById(userDto.getId())).thenReturn(true);
        when(userRepositoryMock.getOne(userDto.getId())).thenReturn(user);


        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(userDto);

        UserDto result = userService.delete(userDto.getId());

        assertEquals(userDto.getId(), result.getId());
        verify(userRepositoryMock).existsById(userDto.getId());
        verify(userRepositoryMock).getOne(userDto.getId());
        verify(conversionServiceMock).convert(user, UserDto.class);

        verify(userRepositoryMock).deleteById(userDto.getId());

        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);

    }

    @Test
    public void test_createUser(){
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build();
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();

        when(conversionServiceMock.convert(userDto, User.class)).thenReturn(user);
        when(userRepositoryMock.save(user)).thenReturn(user);
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(userDto);

        userService.create(userDto);

        verify(conversionServiceMock).convert(userDto, User.class);
        verify(userRepositoryMock).save(user);
        verify(conversionServiceMock).convert(user, UserDto.class);


        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }
    @Test
    public void test_createPassword(){
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .build();
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();

        when(userRepositoryMock.existsById(user.getId())).thenReturn(true);
        when(userRepositoryMock.getOne(user.getId())).thenReturn(user);
        when(conversionServiceMock.convert(user, UserDto.class)).thenReturn(userDto);

        userService.createPassword(1L,"ana");

        verify(userRepositoryMock).getOne(user.getId());
        verify(userRepositoryMock).existsById(user.getId());
        verify(conversionServiceMock).convert(user, UserDto.class);
        verify(userRepositoryMock).executeUpdate("ana", 1L);
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }
    @Test(expected = NotFoundException.class)
    public void test_createPasswordNotFoundException(){
        when(userRepositoryMock.existsById(-100L)).thenReturn(false);

        userService.createPassword(-100L,"ana");

        verify(userRepositoryMock).existsById(-100L);
        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

    @Test
    public void test_userExistByIdIsTrue(){
        User user = User.builder()
                .id(1L)
                .firstName("User")
                .lastName("User")
                .username("user")
                .role("ROLE_USER")
                .email("user@gmail.com")
                .password("test")
                .build();
        when(userRepositoryMock.existsById(user.getId())).thenReturn(true);

        boolean result = userService.existById(1L);
        assertThat(true, is(result));

        verify(userRepositoryMock).existsById(user.getId());

        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);

    }
    @Test
    public void test_userExistsByIdIsFalse(){
        when(userRepositoryMock.existsById(-100L)).thenReturn(false);
        boolean result = userService.existById(-100L);
        assertThat(false, is(result));

        verify(userRepositoryMock).existsById(-100L);

        verifyNoMoreInteractions(userRepositoryMock, conversionServiceMock);
    }

}