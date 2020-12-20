package day_09;

import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;
import utility.ConfigurationReader;

public class JUnit5ParametrizedTest {

    @ParameterizedTest
    @ValueSource(ints = { 5, 6, 7, 8, 9 } )
    public void test1( int myNumber ){

        System.out.println("myNumber = " + myNumber);
        // assert 5,6,7,9 all less than 10
        assertThat( myNumber, is( lessThan(10) ) );
    }

    // using CSV file as source for parametrized test
    @ParameterizedTest
    @CsvFileSource(resources = "/zipcode.csv" , numLinesToSkip = 1)
    public void test2(String zipcode){

        System.out.println("zipcode = " + zipcode);
        // sending request to zipcode endpoint here

        // http://api.zippopotam.us/us/{zipcode}
        // baseurl : api.zippopotam.us
        // endpoint is : us/{zipcode}

        given()
                .log().uri()
                .baseUri("http://api.zippopotam.us")
                .pathParam("zipcode", zipcode).
        when()
                .get("/us/{zipcode}").
        then()
                .assertThat()
                .statusCode( is(200) )
                ;


    }

    // using CSV file as source for parametrized test
    @ParameterizedTest
    @CsvFileSource(resources = "/country_zip.csv" , numLinesToSkip = 1)
    public void testCountryZipCodeCombo(String country, int zipcode){

        given()
                .log().uri()
                .baseUri("http://api.zippopotam.us")
                .pathParam("country", country)
                .pathParam("zipcode", zipcode).
                when()
                .get("/{country}/{zipcode}").
                then()
                .assertThat()
                .statusCode( is(200) )
        ;

    }




}
