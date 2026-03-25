package com.fatecpi.impacto_ext.core.model;

import com.fatecpi.impacto_ext.core.model.enums.ExtintorType;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorWeight;
import com.fatecpi.impacto_ext.core.model.enums.ExtintorStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Extintor extends Produto {

    private UUID id;
    private ExtintorType type;
    private ExtintorWeight weight;
    private ExtintorStatus status;
    private int estoque;
}
