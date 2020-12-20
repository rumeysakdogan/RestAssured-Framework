package day_10;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.Matchers.* ;
import static org.hamcrest.MatcherAssert.*;
import utility.ConfigurationReader;

import java.util.List;

public class NewsAPI_HomeWork {

    /**
     * https://newsapi.org/v2/top-headlines?country=us&apiKey=API_KEY
     * Sen request to above request URL
     * print all article authors if source id is not null
     *  additional requirement -- remove any author with null value
     * Try to store the articles JSON array as List<ArticlePOJO>
     *    with2 fields author name, title, description (exclude if source id is null )
     *    myApiKey : a65aa2967a914f62ab216e25381620ea
     */

    @DisplayName("Get All Articles author if source id is not null")
    @Test
    public void testGetAllArticleAuthor(){

         JsonPath jp =  given()
                                .log().all()
                                .baseUri("https://newsapi.org")
                                .basePath("/v2")
                                .queryParam("country","us")
                                .queryParam("apiKey","a65aa2967a914f62ab216e25381620ea").
                        when()
                                .get("/top-headlines").prettyPeek()
                                .jsonPath();

         List<String> allAuthors = jp.getList("articles.findAll { it.source.id != null && it.author != null }.author");
            allAuthors.forEach(System.out::println);
    }
}
