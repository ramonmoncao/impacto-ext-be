package com.fatecpi.impacto_ext.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes")
public class ClienteEntity {
    @Id
    private UUID id;
    private String name;
    private String cnpj;
    private String cpf;
    private String telefone;
    private String endereco;
}