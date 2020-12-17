package day_01;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Day 1 Hello Test")
public class HelloTest {

    // Junit5 Annotations
    // @BeforeAll @AfterAll @BeforeEach @AfterEach
    // @DisplayName @Disabled

    @BeforeAll
    public static void setUp(){
        System.out.println("@BeforeAll is running");
    }

    @AfterAll
    public static void tearDown(){
        System.out.println("@AfterAll is running");
    }

    @BeforeEach
    public void setUpTest(){
        System.out.println("@BeforeEach is running");
    }

    @AfterEach
    public void tearDownTest(){
        System.out.println("@AfterEach is running");
    }

    @Test @DisplayName("Test 1+3 = 4")
    public void test(){
        System.out.println("test 1 is running");
        assertEquals(4, 3+1);
    }

    @DisplayName("Test 3*4 = 12")
    @Test @Disabled
    public void test2(){
        System.out.println("test 2 is running");
        assertEquals(12,4*3);
    }
}
