package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecuperarContrasenaDTO {

    private String usuario;
    private String dpi;
    private String newPassword;

    public RecuperarContrasenaDTO() {
    }

    public RecuperarContrasenaDTO(String usuario, String dpi, String newPassword) {
        this.usuario = usuario;
        this.dpi = dpi;
        this.newPassword = newPassword;
    }
}
