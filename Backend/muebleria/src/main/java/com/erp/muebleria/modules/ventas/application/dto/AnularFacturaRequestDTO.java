package com.erp.muebleria.modules.ventas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnularFacturaRequestDTO {
    private String motivo;
    private Long empleadoId;
}