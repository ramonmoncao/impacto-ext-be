package com.fatecpi.impacto_ext.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String cpf;
    private String email;
}

