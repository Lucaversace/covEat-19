package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String zipCode;

    @Column(nullable = false)
    @NotNull
    private String city;

    @Column(nullable = false)
    @NotNull
    private String street;

}
