package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
public class Restaurant {
	
    public Restaurant() {
    }

    public Restaurant(String name, String email, String password,  Address address,  String image_url) {
        this.address = address;
        this.name = name;
        this.email = email;
        this.image_url = image_url;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Address address;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    @Email
    private String email;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column(nullable = true)
    private String image_url;
    

}
