package day_04;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;

public class OpenMovieDB_Test {

    //http://www.omdbapi.com/?t=Matrix&apikey=ab665c9c

    @BeforeAll
    public static void setUp(){
        baseURI = "http://www.omdbapi.com";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Test Search Movie or OpenMovie Test")
    @Test
    public void testMovie(){

        given()
                .queryParam("apikey","ab665c9c")
                .queryParam("t","Matrix").
        when()
                .get().prettyPeek().  // our request URL is already complete, we do not need to add anything more
        then()
                .assertThat()
                .statusCode( is(200))
                .contentType(ContentType.JSON)
                .body("Title", is("Matrix"))
                .body("Ratings[0].Source", is("Internet Movie Database"));
    }

    @DisplayName("Getting the log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){

        given()
                .queryParam("apikey","ab665c9c")
                .queryParam("t","John Wick")
                // logging the response should be in then section
                .log().all().
                //.log().uri().
                //.log().parameters().
        when()
                .get().
        then()
                //logging the response should be in then section
                //.log().all()
                .log().ifValidationFails()
                .assertThat()
                .statusCode( is(200) )
                .body("Ratings[1].Source",is("Rotten Tomatoes"))
                .body("Plot", containsString("ex-hit-man") );
    }
}
