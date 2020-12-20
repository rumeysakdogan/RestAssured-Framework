package day_10;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import utility.ConfigurationReader;
import utility.DB_Utility;

import java.util.Map;
import java.util.stream.Stream;

public class ParametrizedTestWithMethodSource {

    @ParameterizedTest
    @MethodSource("getAllCountryIDs")
    public void testDB(String countryID){
        System.out.println("countryID = " + countryID);
    }
    public static Stream<String> getAllCountryIDs(){
        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        return DB_Utility.getColumnDataAsList(1).stream();
    }
    @ParameterizedTest
    @MethodSource("getAllCountryDataAsListOfMapStream")
    public void testAllCountryData(Map<String,String> countryRowMap){
        System.out.println("countryRowMap = " + countryRowMap);
    }
    public static Stream<Map<String,String>> getAllCountryDataAsListOfMapStream(){
        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        return DB_Utility.getAllDataAsListOfMap().stream();
    }
}
