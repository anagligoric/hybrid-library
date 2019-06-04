package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.UserDto;
import com.hybridlibrary.models.User;
import com.hybridlibrary.repositories.UserRepository;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
public class UserRestControllerTest extends BaseRestControllerTest{

    @Autowired
    private UserRepository userRepository;

    public void initData(){
        User user = User.builder()
                .firstName("User")
                .lastName("User")
                .username("username")
                .role("ROLE_USER")
                .email("user@mail.com")
                .password("integrationtest")
                .build();
        userRepository.save(user);
    }

    @Test
    public void getInitialUsers() {
        Response response = given().auth().basic("user", "test").when().get("/user").then().statusCode(200).extract().response();

        UserDto[] users = response.getBody().as(UserDto[].class);
        assertNotNull(users);
    }

    @Test
    public void getOneUser() {
        Response response = given().auth().basic("user", "test").when().get("/user/1").then().statusCode(200).extract().response();

        UserDto user = response.getBody().as(UserDto.class);
        assertNotNull(user);
        assertThat(1L, is(user.getId()));
    }

}