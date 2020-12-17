package day_03;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;

public class GithubRestApiTest {

    // create a test for testing github rest api users/user endpoint


    @DisplayName("test GitHub GET /users/{username} Endpoint ")
    @Test
    public void testGitHub(){

        given()
                .pathParam("username","CybertekSchool").
        when()
                .get("https://api.github.com/users/{username}").
        then()
                .assertThat()
                .statusCode( is(200) )
                .and().assertThat()
                .contentType(ContentType.JSON)
                .and().assertThat()
                .header("server", "GitHub.com")
                .and().assertThat()
                .body("login", is("CybertekSchool"))
                .body("company", is("Cybertek"));
    }
}
