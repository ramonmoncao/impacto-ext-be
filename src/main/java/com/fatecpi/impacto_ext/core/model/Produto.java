package com.fatecpi.impacto_ext.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {

    private UUID id;
    private String name;
    private double price;
    private String description;
    private int estoque;
}
