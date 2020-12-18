package day_08;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Region;
import utility.ConfigurationReader;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.Map;

public class ORDS_API_DB_Test extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void testDBCOnnection(){

        DB_Utility.runQuery("SELECT * FROM REGIONS");
        DB_Utility.displayAllData();

    }

    /**
     * Send an GET /regions/{region_id} request with region_id of 3
     * check status code is 200
     * save it as Region POJO after status check
     * Get your expected result from Database query
     * SELECT * FROM REGIONS
     * SAVE THE THIRD ROW AS A MAP
     * VERIFY THE DATA FROM response match the data from Database
     */
    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data(){

        int myID = 3;
        Region r3 = given()
                                    .pathParam("region_id",myID).
                            when()
                                    .get("/regions/{region_id}").
                            then()
                                    .log().body()
                                    .assertThat()
                                    .statusCode( is(200))
                                    .extract()
                                    .response()
                                    .as(Region.class);
                                     ;

        System.out.println("r3 = " + r3);

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = " + myID);
        Map<String, String> expectedresultMap = DB_Utility.getRowMap(1);

        System.out.println("region3 = " + expectedresultMap);
        // verify the actual result from api response match expected database result

        assertThat(r3.getRegion_id()+"", is(expectedresultMap.get("REGION_ID")));
        assertThat(r3.getRegion_name() ,equalTo( expectedresultMap.get("REGION_NAME")));

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data2() {

        int myID = 3;
        JsonPath jp = given()
                                    .pathParam("region_id", myID).
                            when()
                                    .get("/regions/{region_id}").
                            then()
                                    .log().body()
                                    .assertThat()
                                    .statusCode(is(200))
                                    .extract()
                                    .response()
                                    .jsonPath()
                                    ;

        // save the response json as a Map object
        // Here we are calling the overloaded version of getMap method with 3 params
        // 1. jsonPath String
        // 2. Data type Map key
        // 3. Data type Map value
        // so we can make sure we get exactly what we asked for
        Map<String, String> actualResultMap = jp.getMap("", String.class, String.class);
        // do not need to remove extra links from json result
        // because we are checking key value pair , anything we dont check will not matter
        System.out.println("actualResultMap = " + actualResultMap);

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = " + myID);
        Map<String, String> expectedresultMap = DB_Utility.getRowMap(1);

        assertThat(     actualResultMap.get("region_id")  ,
                equalTo( expectedresultMap.get("REGION_ID") ));

        assertThat( actualResultMap.get("region_name") ,
                equalTo( expectedresultMap.get("REGION_NAME") ));

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Just value by value")
    @Test
    public void testRegionDataFromResponseMatchDB_Data3() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();

        String actualRegionId = jp.getString("region_id");
        String actualRegionName = jp.getString("region_name");

        DB_Utility.runQuery("SELECT REGION_ID, REGION_NAME FROM REGIONS WHERE REGION_ID = " + myID);
        String expectedRegionId = DB_Utility.getColumnDataAtRow(1, "REGION_ID");
        String expectedRegionName = DB_Utility.getColumnDataAtRow(1, "REGION_NAME");

        assertThat(actualRegionId, is(expectedRegionId));
        assertThat(actualRegionName, is(expectedRegionName));


    }

}
