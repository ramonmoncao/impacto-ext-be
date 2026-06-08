package com.fatecpi.impacto_ext.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProdutoResponse {
    private String id;
    private String name;
    private double price;
    private String description;
    private int estoque;
}
