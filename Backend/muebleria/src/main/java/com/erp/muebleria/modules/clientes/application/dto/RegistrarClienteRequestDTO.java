package com.erp.muebleria.modules.clientes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarClienteRequestDTO {
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private Long empleadoId;
}