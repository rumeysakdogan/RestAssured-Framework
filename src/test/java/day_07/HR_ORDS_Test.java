package day_07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Region;
import utility.ConfigurationReader;

public class HR_ORDS_Test {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.90.101.103:1000";
        basePath = "/ords/hr";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Testing /regions/{region_id}")
    @Test
    public void testThirdRegionIsAsia(){

        given()
                .log().all()
                .pathParam("region_id",3).
        when()
                .get("/regions/{region_id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(200))
                .contentType(ContentType.JSON)
                .body("region_name", is("Asia"))
                .body("region_id",is(equalTo(3)))
                ;
    }

    @DisplayName("Save GET /regions/{region_id} response as POJO")
    @Test
    public void testSingleRegionToPOJO(){

        Response response = given()
                .log().all()
                .pathParam("region_id", 3).
                        when()
                .get("/regions/{region_id}");

        JsonPath jp = response.jsonPath();

        Region r3 = jp.getObject("",Region.class);


    }
}
