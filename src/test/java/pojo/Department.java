package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
// @Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Department{

    private int department_id;
    private String department_name ;
    private int manager_id;
    private int location_id;
}
