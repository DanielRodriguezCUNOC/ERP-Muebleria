package com.erp.muebleria.modules.inventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroCatalogoProductoDTO {
    private String nombre;
    private String categoria;
    private Boolean activo;
}