package com.erp.muebleria.modules.reportes.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TopProductosMasIngresos {
    private final Long productoId;
    private final String nombre;
    private final Long cantidadVendida;
    private final BigDecimal totalIngresos;
}
