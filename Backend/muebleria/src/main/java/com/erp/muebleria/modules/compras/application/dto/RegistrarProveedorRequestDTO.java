package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarProveedorRequestDTO {

    private String nombre;
    private String direccion;
    private String telefonoContacto;
    private Long empleadoId;
}
