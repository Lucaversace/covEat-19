package fr.coveat.app.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @NotNull
    private State state;

    @OneToOne
    @NotNull
    private User user;

    @OneToOne
    @NotNull
    private Restaurant restaurant;

    @OneToOne
    @NotNull
    private Address address;

    private float price_total;
    private Date date;
}
