package com.erp.muebleria.modules.administracion.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReporteCompra {

    private final Long compraId;
    private final Long proveedorId;
    private final String nombreProveedor;
    private final LocalDateTime fecha;
    private final BigDecimal total;
    private final String estado;
}
