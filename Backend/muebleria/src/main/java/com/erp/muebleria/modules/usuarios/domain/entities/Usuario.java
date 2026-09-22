package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    private Long id;
    private String nombre;
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
    public void actualizarInformacion(String nombre, String numeroTelefono, Long areaId, Rol nuevoRol) {
        if (nombre != null) this.nombre = nombre;
        if (numeroTelefono != null) this.numeroTelefono = numeroTelefono;
        if (areaId != null) this.areaId = areaId;
        if (nuevoRol != null) this.rol = nuevoRol;
    }
}