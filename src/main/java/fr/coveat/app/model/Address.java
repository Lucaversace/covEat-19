package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    public Address() {
    }

    public Address(Integer zipCode, String city, String street) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Integer zipCode;
    private String city;
    private String street;

}
