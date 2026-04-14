package com.fatecpi.impacto_ext.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class User extends Vendedor{

        private String email;
        private String password;

        public User(String name, String cpf, String email, String password) {
            super(name, cpf);
            this.email = email;
            this.password = password;
        }

}
