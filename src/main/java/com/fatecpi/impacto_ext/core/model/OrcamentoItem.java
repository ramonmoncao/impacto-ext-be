package com.fatecpi.impacto_ext.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrcamentoItem {

    private Produto produto;
    private int quantity;
    private double subtotal;
    private Double desconto;

    public void calcularSubtotal() {
        double val = produto.getPrice() * quantity;

        if (desconto != null) {
            if (desconto < 0 || desconto > 100) {
                throw new IllegalArgumentException("Invalid discount number");
            }
            val -= val * desconto / 100;
        }

        this.subtotal = val;
    }
}
