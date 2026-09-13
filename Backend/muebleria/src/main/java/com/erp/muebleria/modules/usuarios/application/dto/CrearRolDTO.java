package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrearRolDTO {
    private String nombre;
    private String descripcion;
    private List<Long> permisosIds;

    public CrearRolDTO() {
    }

    public CrearRolDTO(String nombre, String descripcion, List<Long> permisosIds) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisosIds = permisosIds;
    }
}
