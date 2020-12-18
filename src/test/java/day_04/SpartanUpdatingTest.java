package day_04;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import testbase.Spartan_TestBase;
import utility.ConfigurationReader;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class SpartanUpdatingTest extends Spartan_TestBase {

    @DisplayName("Test PUT /api/spartans/{id} with String body")
    @Test
    public void testUpdatingSingleSpartanWithStringBody(){

        String updatedStrPayload =   "    {\n" +
                                    "        \"name\": \"Rocket\",\n" +
                                    "        \"gender\": \"Male\",\n" +
                                    "        \"phone\": 9876547800\n" +
                                    "    }" ;


        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .pathParam("id",106)
                .body(updatedStrPayload).
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                // This is how we can check a header exists by checking the value is not null
                // using notNullValue() matcher
                .header("Date", is(notNullValue()))
                .body( emptyString() )
                ;
    }

    @DisplayName("Test PATCH /api/spartans/{id} with String body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        //update the name to B20 Patched
        // { "name" : "B20 Patched"}
        String patchBody = "{ \"name\" : \"B20 Patched\"}";

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .pathParam("id",107)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                // body for 204 response is always empty
                // we can validate it using emptyString() matcher
                .body( is(emptyString()) );
    }

    @DisplayName("Test DELETE /api/spartans/{id} ")
    @Test
    public void testDeletingSingleSpartan(){

        given()
                .auth().basic("admin","admin")
                .pathParam("id",106)
                .log().all().
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204))
                .body( is(emptyString()));
    }


}
