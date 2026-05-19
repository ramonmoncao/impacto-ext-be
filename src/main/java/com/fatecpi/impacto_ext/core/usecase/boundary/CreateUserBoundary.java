package com.fatecpi.impacto_ext.core.usecase.boundary;

import com.fatecpi.impacto_ext.core.model.User;
import java.util.UUID;

public interface CreateUserBoundary {
    UUID execute(User user);
}

