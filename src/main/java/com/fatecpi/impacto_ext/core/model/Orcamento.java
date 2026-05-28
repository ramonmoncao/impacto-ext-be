package com.fatecpi.impacto_ext.core.model;

import com.fatecpi.impacto_ext.core.model.enums.OrcamentoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orcamento {

    private UUID id;
    private Cliente cliente;
    private Vendedor vendedor;
    private LocalDateTime createDateTime;
    private LocalDateTime endDateTime;
    private OrcamentoStatus status;
    private List<OrcamentoItem> produtos;
    private double total;
    private Double desconto;
    private String usuarioResponsavel;

    public void calcularTotal() {

        double val = 0;

        for (OrcamentoItem item : produtos) {
            item.calcularSubtotal();
            val += item.getSubtotal();
        }

        if (desconto != null) {
            if (desconto < 0 || desconto > 100) {
                throw new IllegalArgumentException("Invalid discount number");
            }
            val -= val * desconto / 100;
        }

        this.total = val;
    }

private boolean concluido = false;

// Adicione os getters e setters se não estiver usando o Lombok (@Data)
public boolean isConcluido() {
    return concluido;
}

public void setConcluido(boolean concluido) {
    this.concluido = concluido;
}
}

