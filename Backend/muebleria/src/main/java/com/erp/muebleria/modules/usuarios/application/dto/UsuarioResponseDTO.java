package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String usuario;
    private String rolName;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String usuario, String rolName) {
        this.id = id;
        this.usuario = usuario;
        this.rolName = rolName;
    }
}
