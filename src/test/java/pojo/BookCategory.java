package pojo;

// we want to be able to name the fields any name we want
// rather than being limited to use same name as json object key
// but we need to tell Jackson data-bind
// which json key map to which pojo class field
// we use annotation @JsonProperties for this

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BookCategory {

    @JsonProperty("id")
    private String category_id;
    @JsonProperty("name")
    private String category_name;

}
