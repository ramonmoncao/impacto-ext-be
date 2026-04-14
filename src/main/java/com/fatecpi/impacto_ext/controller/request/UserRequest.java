package com.fatecpi.impacto_ext.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String cpf;
    private String email;
    private String password;
}

