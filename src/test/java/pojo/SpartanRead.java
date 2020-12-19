package pojo;

// This is a POJO class
// used to represent Spartan Data in Response Json
//it has 4 fields : id, name, gender , phone

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SpartanRead {

    private int id;
    private String name;
    private String gender;
    private Long phone;

}
