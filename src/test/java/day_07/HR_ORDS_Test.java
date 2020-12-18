package day_07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Region;
import testbase.HR_ORDS_TestBase;
import utility.ConfigurationReader;

import java.util.List;

public class HR_ORDS_Test extends HR_ORDS_TestBase {

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
                .get("/regions/{region_id}")
                .prettyPeek();

        JsonPath jp = response.jsonPath();

        Region r3 = jp.getObject("",Region.class); // storing JsonPath as POJO
        System.out.println("r3 = " + r3);

        // as method is good if you dont need to pass path means path is "" to store data as POJO
        Region r4 = response.as(Region.class);
        System.out.println("r4 = " + r4);

    }

    @DisplayName("Save GET /regions response as List of  POJO")
    @Test
    public void testSingleRegionToLisOfPOJO(){

        Response response = get("/regions").prettyPeek();

        JsonPath jp = response.jsonPath();

        List<Region> allRegions = jp.getList("items", Region.class);
        allRegions.forEach(System.out::println);


    }
}
