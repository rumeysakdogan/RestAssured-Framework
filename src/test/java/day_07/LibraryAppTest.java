package day_07;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import pojo.BookCategory;
import testbase.Library_TestBase;
import utility.ConfigurationReader;
import utility.LibraryUtilities;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class LibraryAppTest extends Library_TestBase {

    private static String myToken;

    static {

        myToken = LibraryUtilities
                .loginAndGetToken(ConfigurationReader.getProperty("librarian1.username"),
                        ConfigurationReader.getProperty("librarian1.password") );
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

        List<BookCategory> allCategories = jp.getList("", BookCategory.class);
        allCategories.forEach(System.out::println);

        BookCategory num5BC = jp.getObject("[4]", BookCategory.class) ;
        System.out.println("num5BC = " + num5BC);
    }

}
