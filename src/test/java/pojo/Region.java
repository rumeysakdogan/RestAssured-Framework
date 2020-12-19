package pojo;

// we want to ignore any extra key that json has
// other than region_id, region_name
// anything unknown to this pojo class should be ignored

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter @ToString
public class Region {

    private int region_id;
    private String region_name;

}
