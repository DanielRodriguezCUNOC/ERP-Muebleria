package com.erp.muebleria.modules.clientes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CambiarEstadoClienteRequestDTO {
    private Boolean activo;
    private Long empleadoId;
}