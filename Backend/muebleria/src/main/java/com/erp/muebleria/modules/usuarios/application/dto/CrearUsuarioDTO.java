package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearUsuarioDTO {
    private String usuario;
    private String password;
    private Long rolId;

    public CrearUsuarioDTO() {
    }

    public CrearUsuarioDTO(String usuario, String password, Long rolId) {
        this.usuario = usuario;
        this.password = password;
        this.rolId = rolId;
    }
}
