package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Double price;

    @NotNull
    @Column(nullable = false)
    private String description;

    @OneToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

}
