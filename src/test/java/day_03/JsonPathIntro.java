package day_03;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.* ;

import static org.hamcrest.Matchers.* ;


public class JsonPathIntro {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://100.26.101.158:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Extracting data out of Spartan Json Object")
    @Test
    public void test1SpartanPayload(){

        // send a request to get 1 spartan
        // by providing path params with valid id
        // save it into Response object
        // NEW: create an object with type JsonPath
        // by calling the method jsonPath() on response object
        // extract id,name, gender , phone
        // and save it into variable of correct type

        Response response = given()
                                    .pathParam("id", 72).
                             when()
                                    .get("/spartans/{id}")
                                    .prettyPeek();

        //System.out.println("----------------------");
        //response.prettyPrint();
        // prettyPrint() -->> returns String and print
        // prettyPeek() -->> returns Response object and print and continue to chain


        //JsonPath is used to navigate through the json payload
        // and extract the value according to the valid "jsonpath" provided
        JsonPath jp = response.jsonPath();


        int myId = jp.getInt("id");
        String myName = jp.getString("name");
        String myGender = jp.getString("gender");
        Long myPhone = jp.getLong("phone");

        System.out.println("phone = " + myPhone);
        System.out.println("gender = " + myGender);
        System.out.println("name = " + myName);
        System.out.println("id = " + myId);

    }

    @DisplayName("Extracting data from Json Array Response")
    @Test
    public void getAllSpartanExtractData(){


        //Response response = get("/spartans");
        //JsonPath jp = response.jsonPath();

        JsonPath jp = get("/spartans").jsonPath();  // List

        // get the first json object name field and phone field
        System.out.println(" name1stSpartan = " + jp.getString("name[0]"));
        System.out.println(" phone1stSpartan = " + jp.getLong("phone[0]"));

        // get the 7th json object gender field from json array
        System.out.println(" gender7thSpartan = " + jp.getString("gender[6]"));

        // getting all the name fields from the jsonArray Response
        // and storing as a list
        List<String> allNames = jp.getList("name");
        System.out.println("allNames = " + allNames);

        // getting all the phone fields from the jsonArray Response
        // and storing as a list

        List<Long> allPhones = jp.getList("phone");
        System.out.println("allPhones = " + allPhones);

    }

    // send request to this request url
    // http://100.26.101.158:8000/api/spartans/search?nameContains=de&gender=Male
    // get the name of first person in the result
    // get the phone of third person in the result
    // get all names , all phones save it as List
    // save the value of field called empty under pageable in the response
    @DisplayName("Testing /spartans/search and extracting data")
    @Test
    public void testSearch(){

        JsonPath jp = given()
                            .queryParam("nameContains", "de")
                            .queryParam("gender", "Male").
                        when()
                            .get("/spartans/search").jsonPath();

        System.out.println("First person's name: " +
                            jp.getString("content[0].name"));

        System.out.println("Last person's name: " +
                jp.getString("content[-1].name"));

        System.out.println("First 3 people' name: " +
                jp.getString("content[0..3].name"));

        System.out.println("Second person's phone: " +
                            jp.getString("content[2].phone"));

        System.out.println("allNames: " +
                            jp.getList("content.name"));

        System.out.println("Value of field empty: " +
                            jp.getBoolean("pageable.sort.empty"));

    }
}
