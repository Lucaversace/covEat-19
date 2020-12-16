package fr.coveat.app.form;

import lombok.Data;

@Data
public class UserForm {

    private String street;
    private String city;
    private String zipCode;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confPassword;

}
