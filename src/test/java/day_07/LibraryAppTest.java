package day_07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;
import utility.LibraryUtilities;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class LibraryAppTest {

    private static String myToken;

    @BeforeAll
    public static void setUp() {
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1";
        myToken = LibraryUtilities
                .loginAndGetToken(ConfigurationReader.getProperty("librarian1.username"),
                        ConfigurationReader.getProperty("librarian1.password") );
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

        @DisplayName("Testing GET /dashboard_stats Endpoint")
        @Test
        public void testzDashboard_Stats(){

            given()
                    .log().all()
                    .header("x-library-token",myToken).
            when()
                    .get("/dashboard_stats").
            then()
                    .log().all()
                    .assertThat()
                    .statusCode( is(200) )
                    .contentType( ContentType.JSON);

        }

        @DisplayName("Save the result of Get dashboard Stat as Map object")
        @Test
        public void testGetDashBoardStatAsMap(){
                /*
                    {
                        "book_count": "1162",
                        "borrowed_books": "650",
                        "users": "7411"
                    }
                 */

            JsonPath jp =  given()
                                .log().all()
                                .header("x-library-token", myToken).
                            when()
                                .get("/dashboard_stats")
                                .jsonPath();
            // Get the response as a map and print it out
            Map<String, Object> responseJsonAsMap = jp.getMap("");

            System.out.println("responseJsonAsMap = " + responseJsonAsMap);

        }

    @DisplayName("4. Save /get_book_categories response as POJO")
    @Test
    public void testGetBookCategoriesAsPOJO(){

        JsonPath jp = given()
                            .log().all()
                            .header("x-library-token",myToken).
                        when()
                            .get(" /get_book_categories").prettyPeek()
                            .jsonPath();
    }

}
