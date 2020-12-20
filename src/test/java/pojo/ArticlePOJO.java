package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString @Setter @Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticlePOJO {

    private String author;
    private String title;
    private String description;
}
