package com.example.csgo.domain.match;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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
public class MatchIntegrationTest {
    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @org.junit.jupiter.api.Nested
    class CreateMatchTest {
        @Test
        public void testCreateMatch() {
            var newMatch = new MatchRequest(1203L, "de_nuke");

            given()
                    .contentType(ContentType.JSON)
                    .body(newMatch)
                    .when()
                    .post("/matches")
                    .then()
                    .statusCode(201);

            given()
                    .when()
                    .get("/matches")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(5))
                    .body("items[4].matchNumber", equalTo(1203))
                    .body("items.map", hasItem("de_nuke"));

        }

        @Test
        public void testCreateMatch_MatchNumberWrong() {
            MatchRequest matchRequest = new MatchRequest(-300L, "de_nuke");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .post("/matches")
                    .then()
                    .statusCode(400);
        }


        @Test
        public void testCreateMatch_MapWrong() {
            MatchRequest matchRequest = new MatchRequest(1233L, "nothing");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .post("/matches")
                    .then()
                    .statusCode(400);
        }
    }

    @org.junit.jupiter.api.Nested
    class GetMatchesByMapTest {
        @Test
        public void testGetMatchesByMap() {
            given()
                    .when()
                    .get("/matches/map/de_dust2")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(2))
                    .body("items.map", everyItem(equalTo("de_dust2")));
        }

        @Test
        public void testGetMatchesByMap_NoMatches() {
            given()
                    .when()
                    .get("/matches/map/de_cbble")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(0));
        }
    }

    @org.junit.jupiter.api.Nested
    class GetMatchesTest{
        @Test
        public void testGetMatches() {
            given()
                    .when()
                    .get("/matches")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(4))
                    .body("items.map", hasItems("de_dust2", "de_inferno", "de_mirage"));
        }

        @Test
        public void testGetMatchById() {
            given()
                    .when()
                    .get("/matches/1")
                    .then()
                    .statusCode(200)
                    .body("content.matchNumber", is(1001))
                    .body("content.map", is("de_dust2"));
        }

        @Test
        public void testGetMatchByBadId() {
            given()
                    .when()
                    .get("/matches/100")
                    .then()
                    .statusCode(404);
        }


        @Test
        public void testGetTopMatches() {
            given()
                    .when()
                    .get("/matches/top-map-matches")
                    .then()
                    .statusCode(200)
                    .body("count", equalTo(11))
                    .body("items.size()", equalTo(11))
                    .body("items[0].match_id", equalTo(3))
                    .body("items[0].map", equalTo("de_mirage"))
                    .body("items[0].rounds", equalTo(2))
                    .body("items[2].match_id", equalTo(2))
                    .body("items[2].map", equalTo("de_inferno"))
                    .body("items[2].rounds", equalTo(2))
                    .body("items[7].match_id", equalTo(1))
                    .body("items[7].map", equalTo("de_dust2"))
                    .body("items[7].rounds", equalTo(2));
        }

        @Test
        public void testGetMapCounts() {
            given()
                    .when()
                    .get("/matches/maps")
                    .then()
                    .statusCode(200)
                    .body("content.size()", equalTo(3))
                    .body("content.de_dust2", equalTo(2))
                    .body("content.de_inferno", equalTo(1))
                    .body("content.de_mirage", equalTo(1));
        }

    }

    @org.junit.jupiter.api.Nested
    class DeleteMatchTest{
        @Test
        public void testDeleteMatch() {
            given()
                    .when()
                    .delete("/matches/1")
                    .then()
                    .statusCode(204);

            given()
                    .when()
                    .get("/matches")
                    .then()
                    .statusCode(200)
                    .body("items.size()", equalTo(3));
        }

        @Test
        public void testDeleteMatch_NotFound() {
            given()
                    .when()
                    .delete("/matches/100")
                    .then()
                    .statusCode(404);
        }
    }

    @org.junit.jupiter.api.Nested
    class PutMatchTest{
        @Test
        public void testUpdateMatch() {
            MatchRequest matchRequest = new MatchRequest(1001L, "de_inferno");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .put("/matches/1")
                    .then()
                    .statusCode(200)
                    .body("content.matchNumber", is(1001))
                    .body("content.map", is("de_inferno"));
        }

        @Test
        public void testUpdateMatch_NotFound() {
            MatchRequest matchRequest = new MatchRequest(1001L, "de_inferno");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .put("/matches/100")
                    .then()
                    .statusCode(404);
        }

        @Test
        public void testUpdateMatch_MapWrong() {
            MatchRequest matchRequest = new MatchRequest(1001L, "nothing");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .put("/matches/1")
                    .then()
                    .statusCode(400);
        }

        @Test
        public void testUpdateMatch_MatchNumberWrong() {
            MatchRequest matchRequest = new MatchRequest(-300L, "de_inferno");

            given()
                    .contentType(ContentType.JSON)
                    .body(matchRequest)
                    .when()
                    .put("/matches/1")
                    .then()
                    .statusCode(400);
        }
    }
}
