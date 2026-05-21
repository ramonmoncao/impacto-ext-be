package com.fatecpi.impacto_ext.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    private String name; // Mapeia o 'nome' vindo do front
    private String cnpj;
    private String cpf;
    private String telefone;
    private String endereco;
}