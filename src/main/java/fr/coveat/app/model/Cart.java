package fr.coveat.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class Cart {
    private List<CartLineInfo> cart;
}
