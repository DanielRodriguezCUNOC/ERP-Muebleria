package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Rol {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<Permiso> permisos;

    public Rol(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = new ArrayList<>();
    }

    public void agregarPermiso(Permiso permiso) {
        if (!this.permisos.contains(permiso)) this.permisos.add(permiso);
    }

    public void removerPermiso(Permiso permiso) {
        this.permisos.remove(permiso);
    }
}
