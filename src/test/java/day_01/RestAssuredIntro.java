package day_01;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

public class RestAssuredIntro {

    @DisplayName("Spartan GET /api/hello Endpoint Test")
    @Test
    public void TestHello(){

        // This is the public IP I shared for Spartan 2
        // use it if you do not have it
        // http://100.26.101.158:8000/api/hello

        Response response = get("http://100.26.101.158:8000/api/hello");
        //get status code out of this Response object
        System.out.println( "Status code is: " + response.statusCode() );

        //assert the status code is 200
        assertThat(response.statusCode(),is(200));

        // how to pretty print entire response object
        //prettyPrint() --> print and return the body
        String responsePayload = response.prettyPrint();

        //assertThat the body is Hello from Sparta
        assertThat(responsePayload,is("Hello from Sparta"));

        //There are always multiple way to do same thing in RestAssured
        // get the header called Content-Type from the response
        System.out.println( response.getHeader("Content-Type") );
        System.out.println( response.getContentType() );
        System.out.println( response.contentType() );


        // assert that content type is text/plain;charset=UTF-8
        assertThat( response.contentType() ,equalTo("text/plain;charset=UTF-8"));
        assertThat( response.getContentType() ,is("text/plain;charset=UTF-8"));

        // assert response content type contains text
        assertThat(response.contentType(), containsString("text"));

        //assert that Content-Type startWith text
        assertThat(response.contentType(), startsWith("text"));

        // Easy way to work with Content-Type without typing much
        // We can use ContentType Enum from RestAssured to easily get main part content-type
        // ContentType.TEXT -->> text/plain as Enum
        // startWith accept a String object
        // so use toString method to turn ContentType.TEXT to String so we can use it startWith
        assertThat(response.contentType() ,  startsWith( ContentType.TEXT.toString()  ) );
        assertThat(response.contentType() ,  is( not(ContentType.JSON)   ) );


    }
}
