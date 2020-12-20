package testbase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utility.ConfigurationReader;
import utility.LibraryUtilities;

import static io.restassured.RestAssured.*;

public class Library_TestBase {

    @BeforeAll
    public static void setUp() {
        baseURI = ConfigurationReader.getProperty("library1.base.url");
        basePath = "/rest/v1";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

}
