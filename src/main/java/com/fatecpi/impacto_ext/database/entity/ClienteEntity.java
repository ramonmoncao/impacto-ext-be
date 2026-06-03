package com.fatecpi.impacto_ext.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes")
public class ClienteEntity {
    
    @Id
    private String id; // String é o padrão ideal para o _id automático do MongoDB
    
    private String nome; // Usando 'nome' para casar perfeitamente com o JSON do front-end
    private String cnpj;
    private String cpf;
    private String telefone;
    private String endereco;
    private String numero;
    private String bairro;
    private String cidade;
}