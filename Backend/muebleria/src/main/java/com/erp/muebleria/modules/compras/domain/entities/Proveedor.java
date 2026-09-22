package com.erp.muebleria.modules.compras.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    private Long id;
    private String nombre;
    private String direccion;
    private String telefonoContacto;
    private Boolean activo;
}
