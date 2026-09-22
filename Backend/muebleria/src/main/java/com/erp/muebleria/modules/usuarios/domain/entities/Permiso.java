package com.erp.muebleria.modules.usuarios.domain.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Permiso {

    private Long id;
    private String nombre;
    private String descripcion;
}
