package com.fatecpi.impacto_ext.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "orcamentos") 
public class Orcamento {

    @Id
    private String id;
    
    private String nomeCliente;
    private String cnpj;
    private String endereco;
    private String telefone;
    
    private List<ItemOrcamento> itens;
    
    private Double valorTotal;
    private String observacoes;
    private LocalDateTime dataEmissao;
}