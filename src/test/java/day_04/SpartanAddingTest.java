package day_04;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

public class SpartanAddingTest {


    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Test GET /api/spartans with Basic Auth")
    @Test
    public void testAllSpartansWithBasicAuth(){
        given()
                .log().all()
                .auth().basic("admin","admin").
        when()
                .get("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(200) );
    }

    @DisplayName("Add 1 Data with Raw Json string POST /api/spartans")
    @Test
    public void testAddOneData(){

        /*
            {
            "name": "Elmira",
            "gender": "Female",
            "phone": 9876543210
            }
         */

        String newSpartanString =   "    {\n" +
                                    "        \"name\": \"Elmira\",\n" +
                                    "        \"gender\": \"Female\",\n" +
                                    "        \"phone\": 9876543210\n" +
                                    "    }" ;


        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(newSpartanString).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name", is("Elmira"))
                .body("data.phone", is(9876543210L))
                .body("data.gender", is("Female"));

    }

    @DisplayName("Add 1 Data with Map Object POST /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody(){

        Map<String,Object> payloadMap = new LinkedHashMap<>();

        payloadMap.put("name","Yusuf");
        payloadMap.put("gender","Male");
        payloadMap.put("phone",6789765432L);

        System.out.println("payloadMap = " + payloadMap);

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(payloadMap).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name", is("Yusuf"))
                .body("data.phone", is(6789765432L))
                .body("data.gender", is("Male"));

    }

    @DisplayName("Add 1 Data with External json File POST /api/spartans")
    @Test
    public void testAddOneDataWithJsonFileAsBody(){

        // Create a file called singleSpartan.json right under root directory
        // with below content
        /*
                        {
                            "name": "Leyla",
                            "gender": "Female",
                            "phone": 6787865432
                        }
          add below code to point File object to this singleSpartan.json
        */

        File externalJson = new File("singleSpartan.json");

        given()
                .log().all()
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(externalJson).
        when()
                .post("/spartans").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(201) )
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!"))
                .body("data.name", is("Leyla"))
                .body("data.phone", is(6787865432L))
                .body("data.gender", is("Female"));
    }


}
