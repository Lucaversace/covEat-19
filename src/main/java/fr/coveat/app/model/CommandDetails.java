package fr.coveat.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "command_details")
public class CommandDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Command command;

    @OneToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Dish dish;

    @NotNull
    private int quantity;

    @NotNull
    private float price;
}
