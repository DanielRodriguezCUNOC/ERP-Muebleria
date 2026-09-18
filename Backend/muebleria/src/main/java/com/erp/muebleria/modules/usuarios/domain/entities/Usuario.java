package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Usuario {

    private Long id;
    private String name;
    private String usuario;
    private String password;
    private String dpi;
    private String numeroTelefono;
    private Boolean activo;
    private Long areaId;
    private Rol rol;

    public Usuario(Long id, String usuario, String password, Rol rol) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.activo = true;
        this.rol = rol;
    }

    public Usuario(Long id, String usuario, String password, Boolean activo, Rol rolDomain) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.activo = activo;
        this.rol = rolDomain;
    }

    public void desactivar(){
        this.activo = false;
    }

    public void activar(){
        this.activo = true;
    }

    // Método de dominio para actualizar datos operativos del empleado
    public void actualizarInformacion(String name, String numeroTelefono, Long areaId, Rol nuevoRol) {
        if (name != null) this.name = name;
        if (numeroTelefono != null) this.numeroTelefono = numeroTelefono;
        if (areaId != null) this.areaId = areaId;
        if (nuevoRol != null) this.rol = nuevoRol;
    }
}