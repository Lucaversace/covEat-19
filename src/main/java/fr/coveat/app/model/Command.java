package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @JoinColumn(nullable = false)
    @NotNull
    private State state;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull
    private User user;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Restaurant restaurant;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Address address;

    @Column(nullable = false)
    @NotNull
    private float price_total;

    @Column(nullable = false)
    @NotNull
    private Date date;
}
