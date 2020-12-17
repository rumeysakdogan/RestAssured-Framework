package day_05;
// by default the test is running on alphabetical order
// or the test method name
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.MethodOrderer.* ;

// these are all available option for ordering your tests
//@TestMethodOrder(OrderAnnotation.class)
//@TestMethodOrder(Random.class)
//@TestMethodOrder(MethodName.class) ----> default option
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class TestMethodOrderingOnJUnit5 {

    @Order(3)
    @DisplayName("3. Test A Method")
    @Test
    public void testA(){
        System.out.println("running test A");
    }

    @Order(1)
    @DisplayName("1. Test C Method")
    @Test
    public void testC(){
        System.out.println("running test C");
    }

    @Order(4)
    @DisplayName("4. Test D Method")
    @Test
    public void testD(){
        System.out.println("running test D");
    }

    @Order(2)
    @DisplayName("2. Test B Method")
    @Test
    public void testB(){
        System.out.println("running test B");
    }


}
