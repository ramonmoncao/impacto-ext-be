package com.fatecpi.impacto_ext.models;

import lombok.Data;

@Data
public class ItemOrcamento {
    private String descricaoProduto;
    private int quantidade;
    private Double valorUnitario;

    public Double getSubtotal() {
        if (quantidade > 0 && valorUnitario != null) {
            return quantidade * valorUnitario;
        }
        return 0.0;
    }
}