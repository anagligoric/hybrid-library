package com.hybridlibrary.controllers;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseRestControllerTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = port;
        RestAssured.defaultParser = Parser.JSON;

        initData();
    }

    public void initData() {
        // No implementation
    }

}
