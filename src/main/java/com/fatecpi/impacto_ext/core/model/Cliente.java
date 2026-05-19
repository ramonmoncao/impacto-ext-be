package com.fatecpi.impacto_ext.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cliente {

    private UUID id;
    private String name;
    private String cnpj;
    private String cpf;
}
