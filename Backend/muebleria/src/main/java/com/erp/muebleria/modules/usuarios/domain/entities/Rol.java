package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Rol {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
    private List<Permiso> permisos;

    public Rol(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = true;
        this.permisos = new ArrayList<>();
    }

    public Rol(Long id, String nombre, String descripcion, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.permisos = new ArrayList<>();
    }

    public void agregarPermiso(Permiso permiso) {
        if (!this.permisos.contains(permiso)) this.permisos.add(permiso);
    }

    public void removerPermiso(Permiso permiso) {
        this.permisos.remove(permiso);
    }

    public void actualizarInformacion(String nombre, String descripcion) {
        if (nombre != null && !nombre.isBlank()) this.nombre = nombre;
        if (descripcion != null) this.descripcion = descripcion;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }
}
