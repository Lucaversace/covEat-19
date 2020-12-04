package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Double price;

    private String description;

    @OneToOne
    private Restaurant restaurant;

    @Column(name = "image_url")
    private String imageUrl;
}
