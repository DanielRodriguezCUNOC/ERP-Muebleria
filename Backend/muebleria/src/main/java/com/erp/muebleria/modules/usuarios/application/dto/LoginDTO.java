package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    private String usuario;
    private String password;

    public LoginDTO() {
    }

    public LoginDTO(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }
}
