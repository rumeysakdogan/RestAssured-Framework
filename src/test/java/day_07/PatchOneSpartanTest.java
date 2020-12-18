package day_07;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Spartan;
import testbase.Spartan_TestBase;
import utility.ConfigurationReader;

import java.util.LinkedHashMap;
import java.util.Map;

public class PatchOneSpartanTest extends Spartan_TestBase {

    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdate(){

        // we just want to update the name and phone number

        Map<String,Object> patchBodyMap = new LinkedHashMap<>();
        patchBodyMap.put("name","B20 Voila");
        patchBodyMap.put("phone",9087654321L);

        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",8)
                .contentType(ContentType.JSON)
                .body(patchBodyMap).
        when()
                .patch("/spartans/{id}").
        then()
                .assertThat()
                .statusCode( is(204) );
    }

    @DisplayName("Patching 1 data with Java Object")
    @Test
    public void testPath1DataPartialUpdateWithPOJO(){

        // we just want to update the name and phone number

        Spartan sp = new Spartan(); // ("B20 Voila", "", 1111111111L)
        sp.setName("B20 Voila");
        sp.setPhone(1111111111L);
        // MAP IS A BETTER OPTION WITH MINIMAL EFFORT
        // POJO class need some handling to ignore empty field values
        // when being serialized
        given()
                .auth().basic("admin","admin")
                .log().all()
                .pathParam("id",8)
                .contentType(ContentType.JSON)
                .body( sp ).
        when()
                .patch("/spartans/{id}").
        then()
                .assertThat()
                .statusCode( is(500) );
    }

}
