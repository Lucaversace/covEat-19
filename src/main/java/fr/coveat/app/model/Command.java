package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private State state;

    @OneToOne
    private User user;

    @OneToOne
    private Restaurant restaurant;

    @OneToOne
    private Address address;

    private float price_total;

    private Date date;
}
