package com.fatecpi.impacto_ext.controller.request;

import com.fatecpi.impacto_ext.core.model.enums.ExtintorType;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorWeight;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ExtintorRequest {
    private String name;
    private double price;
    private String description;
    private int estoque;
    private ExtintorType type;
    private ExtintorWeight weight;
    private ExtintorStatus status;
}

