package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.Extintor;
import java.util.UUID;

public interface CreateExtintorBoundary {
    String execute(Extintor extintor);
}

