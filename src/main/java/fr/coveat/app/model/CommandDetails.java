package fr.coveat.app.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CommandDetails")
public class CommandDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @NotNull
    private Command command;

    @OneToOne
    @NotNull
    private Dish dish;

    private int quantity;
    private float price;
}
