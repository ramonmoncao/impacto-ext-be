package com.fatecpi.impacto_ext.controller.response;

import com.fatecpi.impacto_ext.core.model.enums.ExtintorType;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExtintorResponse {
    private String id;
    private String name;
    private double price;
    private String description;
    private int estoque;
    private ExtintorType type;
    private int weight;
    private ExtintorStatus status;
}
