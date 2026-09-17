package com.erp.muebleria.modules.administracion.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ResumenVentasPeriodo {

    private final String periodo;
    private final BigDecimal totalIngresos;
    private final Long totalFacturas;
}
