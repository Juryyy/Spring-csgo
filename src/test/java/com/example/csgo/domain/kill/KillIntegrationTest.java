package com.example.csgo.domain.kill;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class KillIntegrationTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @org.junit.jupiter.api.Nested
    class GetKillTest{
        @Test
        public void testGetAverageKillCount() {
            given()
                    .when()
                    .get("/kills/avg")
                    .then()
                    .statusCode(200)
                    .body("content", notNullValue());
        }

        @Test
        public void testGetAverageKillCountOnMap() {
            given()
                    .when()
                    .get("/kills/avg/de_dust2")
                    .then()
                    .statusCode(200)
                    .body("content.de_dust2", notNullValue());
        }

        @Test
        public void testGetAverageKillCountOnMapNotExist() {
            given()
                    .when()
                    .get("/kills/avg/de_baggage")
                    .then()
                    .statusCode(404);
        }

        @Test
        public void testGetKillCountForWeapons() {
            given()
                    .when()
                    .get("/kills/weapons")
                    .then()
                    .statusCode(200)
                    .body("content", notNullValue());
        }

        @Test
        public void testGetAverageKillCountForSides() {
            given()
                    .when()
                    .get("/kills/avg/sides")
                    .then()
                    .statusCode(200)
                    .body("content", notNullValue());
        }
    }

}