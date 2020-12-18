package day_08;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import testbase.HR_ORDS_TestBase;

import java.util.List;

import static io.restassured.RestAssured.*;

public class HR_ORDS_Test extends HR_ORDS_TestBase {


    @DisplayName("Test GET /countries/{country_id} to POJO")
    @Test
    public void testCountryResponseToPOJO(){

        Response response = get("/countries/{country_id}", "AR").prettyPeek();

        Country ar = response.as(Country.class);
        System.out.println("Argentina = " + ar);

        Country ar1 = response.jsonPath().getObject("", Country.class);
        System.out.println("Argentina with jsonPath = " + ar1);
    }

    @DisplayName("Test GET /countries to List of POJO")
    @Test
    public void testAllCountriesResponseToListOfPOJO(){

        JsonPath jp = get("/countries").prettyPeek().jsonPath();

        List<Country> countryList = jp.getList("items", Country.class);
        countryList.forEach(System.out::println);
    }
}
