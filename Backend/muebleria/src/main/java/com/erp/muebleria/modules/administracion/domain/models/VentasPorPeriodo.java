package com.erp.muebleria.modules.administracion.domain.models;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class VentasPorPeriodo {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private BigDecimal totalIngresos;
    private Integer totalFacturas;
}
