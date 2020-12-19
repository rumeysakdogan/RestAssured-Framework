package day_09;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Country;
import testbase.HR_ORDS_TestBase;
import utility.ConfigurationReader;
import utility.DB_Utility;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ORDS_API_DB_PracticeTest extends HR_ORDS_TestBase {

    @DisplayName("GET /countries/{country_id} Compare Result with DB")
    @Test
    public void testResponseMatchDatabaseData(){

        String myCountryID = "AR" ;
        // send request to /countries/{country_id} for AR
        // save the result as Country POJO

        Country arPOJO =    given()
                                .pathParam("country_id" , myCountryID).
                            when()
                                .get("/countries/{country_id}")
                                .prettyPeek()
                                .as(Country.class);

        // here the shorter way of above code
//        Country arPOJO2 = get( "/countries/{country_id}", myCountryID ).as(Country.class) ;

        System.out.println("arPOJO = " + arPOJO);

        String query = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = '" + myCountryID + "'  " ;
        System.out.println("query = " + query);
        DB_Utility.runQuery( query );
        Map<String, String> dbResultMap = DB_Utility.getFirstRowMap();

        // now start validating the actual response to expected result from database
        assertThat( arPOJO.getCountry_id()  ,is(dbResultMap.get("COUNTRY_ID") ) );
        assertThat( arPOJO.getCountry_name()  ,is(dbResultMap.get("COUNTRY_NAME") ) );

        // save region_id from the map as number
        int expectedRegionId = Integer.parseInt(dbResultMap.get("REGION_ID"));
        assertThat( arPOJO.getRegion_id() , equalTo( expectedRegionId ) );

    }

    @DisplayName("GET /countries Capture All CountryID and Compare Result with DB")
    @Test
    public void testResponseAllCountryIDsMatchDatabaseData(){

        List<String> allCountriesIds = get("/countries").jsonPath().getList("items.country_id");

        //allCountriesIds.forEach(System.out::println);

        DB_Utility.runQuery("SELECT COUNTRY_ID FROM COUNTRIES");
        List<String> expectedListFromDB = DB_Utility.getColumnDataAsList("COUNTRY_ID");

        assertThat( allCountriesIds , equalTo( expectedListFromDB ) );
    }
}
