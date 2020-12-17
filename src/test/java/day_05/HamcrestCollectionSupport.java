package day_05;

import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HamcrestCollectionSupport {

    @Test
    public void testList(){

        List<Integer> numList = Arrays.asList(4,5,6,7,88,90);

        // use hamcrest matcher to test the size of this list
        assertThat( numList , hasSize(6) );

        //assert that this list contains 90
        assertThat( numList , hasItem(90) );

        //assert that this list contains 90, 88
        assertThat( numList, hasItems( 88, 90));

        assertThat( numList , everyItem( is( greaterThan(3) ) ) );

        List<String> allNames  = Arrays.asList("Hogan", "Mariana","Olivia","Gulbadan","Ayxamgul","Kareem","Virginia","Tahir Zohra") ;

        assertThat( allNames , hasSize(8) );
        // check every items has letter a
        assertThat( allNames , everyItem( containsString("a")));
        // check every items has letter a in case insensitive manner
        assertThat(allNames , everyItem(   containsStringIgnoringCase("a")   )     );

        // how to do and or in hamcrest syntax
        // allOf --> and logic , all of the matchers should match or it fails
        assertThat("Murat Degirmenci", allOf(startsWith("Mu"), containsString("men"))) ;

        //  anyOf -->> or logic  as long as one matcher match it will pass
        assertThat("Ramazan Alic" , anyOf(  is("Ramazan") ,  endsWith("ic") )   ) ;


    }

}
