package day_06;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;

import pojo.Spartan;
import pojo.SpartanRead;
import utility.ConfigurationReader;

import java.util.List;
import java.util.Map;

public class JsonToJavaObject {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Get 1 Data With Save Response Json as Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsMap() {

        Response response = given()
                .auth().basic("admin", "admin")
                .pathParam("id", 1)
                .log().all().
                        when()
                .get("/spartans/{id}").prettyPeek();

        // get jsonPath object
        JsonPath responseJson = response.jsonPath();

        Map<String, Object> responseMap = responseJson.getMap("");
        System.out.println("responseMap = " + responseMap);

        SpartanRead sp = responseJson.getObject("", SpartanRead.class);
        System.out.println("sp = " + sp);


        /**
         * {
         *     "id": 1,
         *     "name": "Santana",
         *     "gender": "Male",
         *     "phone": 8634292857
         * }
         * jsonPath to get whole json object is just empty string
         *
         * assume this is a car object
         * {
         *      "make" : "Honda"
         *      "color" : "white"
         *      "engine" : {
         *                      "type" : "v8"
         *                      "horsepower" : 350
         *                 }
         * }
         * jsonPath for horse power --> engine.horsepower
         * jsonPath for engine itself --> engine
         * jsonPath for entire car JsonObject --> ""
         *
         *
         */

    }


        @DisplayName("Get All Spartans Data And Save Response Json Array as Java Object")
        @Test
        public void getAllSpartansAndSaveResponseJsonAsJavaObject() {

          Response response = given()
                                    .auth().basic("admin", "admin").
                                when()
                                    .get("/spartans");

          JsonPath jp = response.jsonPath();
            List<SpartanRead> allSpartanPOJOs = jp.getList("", SpartanRead.class);

            //System.out.println("allSpartanPOJOs = " + allSpartanPOJOs);
            allSpartanPOJOs.forEach(System.out::println);
            //allSpartanPOJOs.forEach(soutc); shortcut


        }
}
