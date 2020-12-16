package fr.coveat.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartLineInfo {

    private Long dishId;
    private int quantity;

}
