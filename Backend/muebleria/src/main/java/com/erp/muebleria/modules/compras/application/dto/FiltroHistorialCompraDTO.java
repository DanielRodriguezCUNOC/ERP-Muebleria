package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroHistorialCompraDTO {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long proveedorId;
    private Long empleadoId;
}