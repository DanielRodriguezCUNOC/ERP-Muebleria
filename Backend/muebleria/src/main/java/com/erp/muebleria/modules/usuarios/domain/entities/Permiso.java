package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permiso {

    private Long id;
    private String nombre;
    private String descripcion;

    public Permiso(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
}
