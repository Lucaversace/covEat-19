package fr.coveat.app.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class User {

    public User() {
    }

    public User(String lastname, String firstname, String email, String password,  Address address) {
        this.address = address;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private Address address;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
