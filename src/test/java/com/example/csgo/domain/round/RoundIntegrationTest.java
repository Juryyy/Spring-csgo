package com.example.csgo.domain.round;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class RoundIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @org.junit.jupiter.api.Nested
    class CreateRoundTest {
        @Test
        public void testCreateRound() {
            var newRound = new RoundRequest(3L, 3, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(201);

            given()
                    .when()
                    .get("/rounds")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(7))
                    .body("items[6].id", notNullValue())
                    .body("items[6].match.id", equalTo(3))
                    .body("items[6].round", equalTo(3))
                    .body("items[6].winnerSide", equalTo("CounterTerrorist"))
                    .body("items[6].ctEqVal", equalTo(10000))
                    .body("items[6].teqVal", equalTo(9250));
        }

        @Test
        public void testCreateRound_MatchIdWrong() {
            var newRound = new RoundRequest(-3L, 3, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(404);
        }

        @Test
        public void testCreateRound_RoundWrong() {
            var newRound = new RoundRequest(3L, -3, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testCreateRound_WinnerSideWrong() {
            var newRound = new RoundRequest(3L, 3, "nothing", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testCreateRound_CtEqValWrong() {
            var newRound = new RoundRequest(3L, 3, "CounterTerrorist", -10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testCreateRound_TEqValWrong() {
            var newRound = new RoundRequest(3L, 3, "CounterTerrorist", 10000, -9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testCreateRound_MatchNotFound() {
            var newRound = new RoundRequest(100L, 3, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(newRound)
                    .when()
                    .post("/rounds")
                    .then()
                    .statusCode(404);
        }


    }

    @org.junit.jupiter.api.Nested
    class UpdateRoundTest {
        @Test
        public void testUpdateRound() {
            var updatedRound = new RoundRequest(1L, 1, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(202);

        //?Don't ask me why, it puts the updated round at the end of a list during the check, therefore items[5]
            given()
                    .when()
                    .get("/rounds")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(6))
                    .body("items[5].id", equalTo(1))
                    .body("items[5].match.id", equalTo(1))
                    .body("items[5].round", equalTo(1))
                    .body("items[5].winnerSide", equalTo("CounterTerrorist"))
                    .body("items[5].ctEqVal", equalTo(10000))
                    .body("items[5].teqVal", equalTo(9250));
        }

        @Test
        public void testUpdateRound_MatchIdWrong() {
            var updatedRound = new RoundRequest(-1L, 1, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(404);
        }

        @Test
        public void testUpdateRound_RoundWrong() {
            var updatedRound = new RoundRequest(1L, -1, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testUpdateRound_WinnerSideWrong() {
            var updatedRound = new RoundRequest(1L, 1, "nothing", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testUpdateRound_CtEqValWrong() {
            var updatedRound = new RoundRequest(1L, 1, "CounterTerrorist", -10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testUpdateRound_TEqValWrong() {
            var updatedRound = new RoundRequest(1L, 1, "CounterTerrorist", 10000, -9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/1")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testUpdateRound_RoundNotFound() {
            var updatedRound = new RoundRequest(1L, 1, "CounterTerrorist", 10000, 9250);

            given()
                    .contentType(ContentType.JSON)
                    .body(updatedRound)
                    .when()
                    .put("/rounds/100")
                    .then()
                    .statusCode(404);
        }
    }

    @org.junit.jupiter.api.Nested
    class DeleteRoundTest {
        @Test
        public void testDeleteRound() {
            given()
                    .when()
                    .delete("/rounds/1")
                    .then()
                    .statusCode(204);

            given()
                    .when()
                    .get("/rounds")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(5));
        }

        @Test
        public void testDeleteRound_RoundNotFound() {
            given()
                    .when()
                    .delete("/rounds/100")
                    .then()
                    .statusCode(404);
        }
    }

}
