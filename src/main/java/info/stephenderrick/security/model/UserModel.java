package info.stephenderrick.security.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserModel {
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;


}
