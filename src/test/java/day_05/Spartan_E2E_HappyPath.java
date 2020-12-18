package day_05;

import io.restassured.http.ContentType;
import testbase.Spartan_TestBase;
import utility.ConfigurationReader;
import org.junit.jupiter.api.*;
import utility.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Spartan App End to End CRUD Happy Path")
public class Spartan_E2E_HappyPath extends Spartan_TestBase {

    private static Map<String, Object> payloadMap;
    private static int newID;

    static {
        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();
    }

    // crud operation -- CREATE -- READ -- UPDATE -- DELETE

    @DisplayName("1. Testing POST /api/spartans Endpoint")
    @Test
    public void testAddData(){

        newID = given()
                            .auth().basic("admin", "admin")
                            .contentType(ContentType.JSON)
                            .body( payloadMap )
                            .log().all().
                    when()
                            .post("/spartans").
                    then()
                            .log().all()
                            .assertThat()
                            .statusCode( is(201) )
                            .contentType( ContentType.JSON)
                            .body("data.name", is ( payloadMap.get("name")) )
                            .body("data.gender", is ( payloadMap.get("gender")) )
                            .body("data.phone", equalTo ( payloadMap.get("phone")) )
                        .extract()
                            .jsonPath()
                            .getInt("data.id")
                            ;
        System.out.println("newID = " + newID);
    }


    @DisplayName("2. Testing GET /api/spartans/{id} Endpoint")
    @Test
    public void testGet1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is (200) )
                .contentType(ContentType.JSON)
                .body("id" , is(newID) )
                .body("name" , is( payloadMap.get("name")  ) )
                .body("gender" , is( payloadMap.get("gender") ) )
                .body("phone" , is( payloadMap.get("phone") ) )
                ;

        }

    @DisplayName("3. Testing PUT /api/spartans/{id} Endpoint")
    @Test
    public void testUpdate1SpartanData(){

        // We want to have different payload so we can update
        // Option is rerun the utility method to override
        // existing map object with newly generated faker map object
        payloadMap = SpartanUtil.getRandomSpartanRequestPayload();
//        System.out.println("payloadMap = " + payloadMap);
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .contentType( ContentType.JSON )
                .body(payloadMap) // updated payload
                .log().all().
                when()
                .put("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                .body( emptyString() )
              ;
        // in order to make sure the update actually happened
        // i want to make another get request to this ID
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
                when()
                .get("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode( is (200) )
                .contentType(ContentType.JSON)
                .body("id" , is(newID) )
                .body("name" , is( payloadMap.get("name")  ) )
                .body("gender" , is( payloadMap.get("gender") ) )
                .body("phone" , is( payloadMap.get("phone") ) )
        ;

    }

    @DisplayName("4. Testing DELETE /api/spartans/{id} Endpoint")
    @Test
    public void testDelete1SpartanData(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                .body( is (emptyString() ) ) ;

        // in order to make sure the delete actually happened
        // i want to make another get request to this ID expect 404
        given()
                .auth().basic("admin","admin")
                .pathParam("id" , newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is (404) ) ;

    }




}
