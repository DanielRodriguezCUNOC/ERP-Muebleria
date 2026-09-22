package com.erp.muebleria.modules.reportes.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MovimientoProducto {
    private final Long id;
    private final Long productoId;
    private final Integer cantidadCambio;
    private final Integer existenciaResultante;
    private final String tipoMovimiento;
    private final String origenTipo;
    private final Long origenId;
    private final LocalDateTime fecha;
}
