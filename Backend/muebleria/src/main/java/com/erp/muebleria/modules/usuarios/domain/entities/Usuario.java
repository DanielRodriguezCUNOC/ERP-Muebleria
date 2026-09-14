package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    private Long id;
    private String usuario;
    private String password;
    private Boolean activo;
    private Rol rol;

    public Usuario(Long id, String usuario, String password, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.activo = true;
        this.rol = rol;
    }

    public void desactivar(){
        this.activo = false;
    }

    public void activar(){
        this.activo = true;
    }
}
