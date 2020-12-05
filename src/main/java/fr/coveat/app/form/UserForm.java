package fr.coveat.app.form;

import fr.coveat.app.model.Address;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserForm {

    private String street;
    private String city;
    private Integer zipCode;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
