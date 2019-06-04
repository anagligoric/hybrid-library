package com.hybridlibrary.controllers;

import com.hybridlibrary.dtos.BookDto;
import com.hybridlibrary.models.Book;
import com.hybridlibrary.repositories.BookRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

public class BookRestControllerTest extends BaseRestControllerTest{

    @Autowired
    private BookRepository bookRepository;

    public void initData(){
        Book book = Book.builder()
                .id(1L)
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();
        bookRepository.save(book);
    }

    @Test
    public void getInitialBooks() {
        Response response = given().auth().basic("user", "test").when().get("/book").then().statusCode(200).extract().response();

        BookDto[] books = response.getBody().as(BookDto[].class);
        assertNotNull(books);
    }
    @Test
    public void getOneBook() {
        Response response = given().auth().basic("user", "test").when().get("/book/1").then().statusCode(200).extract().response();

        BookDto book = response.getBody().as(BookDto.class);
        assertNotNull(book);

    }

    @Test
    public void postOneBook(){
        BookDto request = BookDto.builder()
                .title("Title")
                .author("Author")
                .language("Language")
                .rentPeriod(120)
                .build();

        Response response = given().contentType(ContentType.JSON).body(request).auth().basic("user", "test").when().post("/book").then().statusCode(200).extract().response();

        BookDto book = response.getBody().as(BookDto.class);
        assertNotNull(book);
    }



    @Test
    public void getNonExistingBook(){
        given().auth().basic("user", "test").when().get("/book/-100").then().statusCode(404);
    }


}