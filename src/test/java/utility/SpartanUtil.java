package utility;

import com.github.javafaker.Faker;
import pojo.Spartan;

import java.util.LinkedHashMap;
import java.util.Map;

public class SpartanUtil {

    public static Map<String,Object> getRandomSpartanRequestPayload() {

        Faker faker = new Faker();

        Map<String,Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name", faker.name().firstName());
        payloadMap.put("gender", faker.demographic().sex());
        payloadMap.put("phone",faker.number().numberBetween(5000000000L,9999999999L));

        return payloadMap;
    }

    public static Spartan getRandomSpartanPOJO_Payload(){

        Faker faker = new Faker() ;

        Spartan randomSpartan = new Spartan();
        randomSpartan.setName(faker.name().firstName());
        randomSpartan.setGender(faker.demographic().sex());
        randomSpartan.setPhone(faker.number().numberBetween(5000000000L, 9999999999L));

        return randomSpartan ;
    }

}
