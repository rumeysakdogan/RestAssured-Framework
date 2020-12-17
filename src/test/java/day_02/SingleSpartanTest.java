package day_02;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;

public class SingleSpartanTest {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://100.26.101.158:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Testing GET /spartans/{id} Endpoint")
    @Test
    public void test1Spartan(){

        // I want to get json result out
        // When I send GET request to /spartans/{id} endpoint
        // and expecting 200 status code


        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/49").
        then()
                .assertThat()
                .statusCode( is(200) )
                .contentType(ContentType.JSON);

        // I want to make it obvious for
        // the value 49 is path variable | params
        // to uniquely identify the resource

        given()
                .accept(ContentType.JSON)
                .pathParam("id",49).
        when()
                .get("/spartans/{id}").
        then()
                .assertThat()
                .statusCode( is(200) )
                .and()
                .contentType(ContentType.JSON);

        // This is the easiest one, same result

        when()
                .get("/spartans/{id}",49).
        then()
                .assertThat()
                .statusCode( is(200) )
                .and()
                .contentType(ContentType.JSON);
    }

    @DisplayName("Testing GET /spartans/{id} Endpoint")
    @Test
    public void test1SpartanPayload(){

        /**
         * {
         *     "id": 49,
         *     "name": "B20 VOLA",
         *     "gender": "Female",
         *     "phone": 3216547890
         * }
         */

        when()
                .get("/spartans/{id}",49).
                then()
                .assertThat()
                .statusCode( is(200) )
                .and()
                .contentType(ContentType.JSON)
                .body("id",equalTo(49) )
                .body("gender", is("Female"))
                .body("name", is(equalTo("B20 VOLA")))
                .body("phone",is(3216547890L));

    }

}
