package day_10;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Spartan;
import testbase.Spartan_TestBase;
import utility.ConfigurationReader;
import utility.SpartanUtil;

public class SpartanWithReusableSpecForAdminRoleTest {

    static RequestSpecification requestSpec;
    static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";

        requestSpec = given().log().all().auth().basic("admin","admin");
        responseSpec = expect().logDetail(LogDetail.ALL).statusCode( is(200)).contentType(ContentType.JSON);

    }

    @DisplayName("GET /api/spartans Endpoint Test")
    @Test
    public void testAllSpartan(){

        // log().all() will not work with expect()
        // in order to make it work we need to use different method
        // logDetail(LogDetail.ALL) to provide how much we want to log

        given()
               .spec(requestSpec).
        when()
                .get("/spartans").
        then()
               .spec(responseSpec);
    }

    @DisplayName("POST /api/spartans Endpoint Test")
    @Test
    public void testPost1Data(){

        Spartan randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();

        RequestSpecification postRequestSpec = given().spec(requestSpec)
                                                  .contentType( ContentType.JSON)
                                                  .body( randomSpartanPayload );

        ResponseSpecification postResponseSpec = expect().logDetail(LogDetail.ALL)
                                                        .statusCode(is(201))
                                                        .contentType(ContentType.JSON)
                                            .body("success", is("A Spartan is Born!"));

        given()
                .spec( postRequestSpec ).
        when()
                .post("/spartans").
        then()
                .spec( postResponseSpec);

    }

    @DisplayName("GET /api/spartans/{id} Endpoint Test")
    @Test
    public void testOneSpartan(){

        given()
                .spec( requestSpec )
                .pathParam("id",1).
        when()
                .get("/spartans/{id}").
        then()
                .spec( responseSpec )
                ;

        requestSpec
                   .pathParam("id",5).
                when()
                   .get("/spartans/{id}").
                then()
                    .spec( responseSpec )
                    ;
    }
}
