package com.fatecpi.impacto_ext.database.entity;

import com.fatecpi.impacto_ext.core.model.enums.ExtintorStatus;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorType;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorWeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "produto")
@Builder
@Getter
@Setter
@AllArgsConstructor
@FieldNameConstants
public class ProdutoEntity{

    @Id
    private String id;
    private String name;
    private double price;
    private String description;
    private int estoque;
    private ExtintorType type;
    private ExtintorWeight weight;
    private ExtintorStatus status;
}
