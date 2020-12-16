package fr.coveat.app.form;

import lombok.Data;

@Data
public class RestorerForm {

    private String email;
    private String image_url;
    private String name;
    private String password;
    private String conf_password;
    private String street;
    private String city;
    private String zipCode;
    
    }
