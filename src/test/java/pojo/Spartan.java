package pojo;

//A Pojo (Plain Old java Object) class
// is used to create object that represent data
// it must have
// encapsulated fields
// public no arg constructor

import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Spartan {

    private String name;
    private String gender;
    private Long phone;

}
