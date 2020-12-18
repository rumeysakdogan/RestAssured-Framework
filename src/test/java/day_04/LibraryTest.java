package day_04;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import testbase.Library_TestBase;

import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;

class LibrayAppTest extends Library_TestBase {

    private static String myToken;

    @DisplayName("Testing /login Endpoint")
    @Test
    public void testLogin() {
        /*
            Librarian1  username	librarian69@library
            Librarian1 password		KNPXrm3S
         */

         myToken = given()
                            .log().all()
                            .contentType(ContentType.URLENC)
                            .formParam("email", "librarian69@library")
                            .formParam("password", "KNPXrm3S").
                         when()
                            .post("/login").
                        then()
                            .log().all()
                            .assertThat()
                            .statusCode(is(200))
                            .contentType(ContentType.JSON)
                            .body("token", is(not(emptyString())))
                            .extract()
                            .jsonPath()
                            .getString("token");

        System.out.println("myToken = \n" + myToken);
        // How to extract some data out of response object
        // after doing validation in then section
        // without breaking the chain -->> use extract() method that return
    }

        @DisplayName("Testing /dashboard_stats Endpoint")
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

}
