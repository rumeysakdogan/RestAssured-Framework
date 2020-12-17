package day_01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;  // next to . --> ctrl+space will display all matchers

public class HamcrestMatchersTest {

    // Matchers that can be combined to create flexible expressions of intent
    // Hamcrest Assertion Library already part of our RestAssured Dependency in pom file
    // No separate dependency needed.

    @DisplayName("Test 1 +3 = 4")
    @Test
    public void test1() {
        // is() --> hamcrest matcher
        assertThat(1 + 3, is(4));

        assertThat(1 + 3, equalTo(4));
        // add some nice error message if it fails
        assertThat("Wrong Result", 1 + 3, equalTo(5));

        // test 1+3 is not 5
        assertThat(1 + 3, not(5)); // T means any value

        // we can nest a matcher inside another matcher
        assertThat(1 + 3, is(not(5)));
        assertThat(1 + 3, not(equalTo(5)));

        // test 1+3 is less than 5
        assertThat(1 + 3, lessThan(5));
        // test 1+4 is more than 2
        assertThat(1 + 3, greaterThan(2));
    }

    @Test
    @DisplayName("Common Matchers for Strings")
    public void testString() {

        String str = "RestAssured is cool so far";

        // assert the str is "RestAssured is cool so far"
        assertThat(str, is("RestAssured is cool so far"));

        // assert the str is "RestAssured IS COOL so far"
        assertThat(str, equalToIgnoringCase("RestAssured IS COOL so far"));

        // assert the str startsWith "Rest"
        assertThat(str, startsWith("Rest"));

        // assert that str ends with "so far"
        assertThat(str, endsWith("so far"));

        // assert str contains "is cool"
        assertThat(str, containsString("is cool"));

        // assert the str contains "IS COOL" case insensitive manner
        assertThat(str, containsStringIgnoringCase("IS COOL"));
    }

}

