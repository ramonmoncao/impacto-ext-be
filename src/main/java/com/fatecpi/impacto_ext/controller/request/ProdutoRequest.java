package com.fatecpi.impacto_ext.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ProdutoRequest {

    private String name;
    private double price;
    private String description;
    private int estoque;
}
