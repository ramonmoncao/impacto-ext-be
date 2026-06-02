package com.fatecpi.impacto_ext.database.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientes")
public class ClienteEntity {
    
    @Id
    private String id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;

}