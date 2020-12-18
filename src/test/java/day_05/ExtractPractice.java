package day_05;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import testbase.Spartan_TestBase;
import utility.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class ExtractPractice extends Spartan_TestBase {

    /*
     extract() method of RestAssured
     enable you to extract data after validation
     in then section of the method chaining
     */

    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){

        // search for nameContains : a , gender Female
        // verify status code is 200
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        // compare those 2

        JsonPath jp =   given()
                            .log().all()
                            .auth().basic("admin", "admin")
                            .queryParam("nameContains", "a")
                            .queryParam("gender", "Female").
                        when()
                            .get("/spartans/search").
                        then()
                            .log().all()
                            .assertThat()
                            .statusCode(is(200))
                            .extract()
                            .jsonPath();

        List<String> allNames = jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        int numOfElements = jp.getInt("numberOfElements");
        System.out.println("numOfElements = " + numOfElements);
        assertThat( allNames.size() , equalTo( numOfElements ) );

        //using hamcrest matcher collection support for asserting the size
        assertThat( allNames , hasSize(numOfElements)  );

    }
}
