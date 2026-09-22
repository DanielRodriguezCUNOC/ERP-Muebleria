package com.erp.muebleria.modules.usuarios.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PermisoResponseDTO {

    private Long id;
    private String codigo;
    private String descripcion;
}
