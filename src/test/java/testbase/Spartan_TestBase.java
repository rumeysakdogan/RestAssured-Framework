package testbase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class Spartan_TestBase {

    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.157.181.196:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }
}
