package com.erp.muebleria.modules.reportes.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TopCliente {

    private Long clienteId;
    private String nombre;
    private String nit;
    private BigDecimal montoTotal;
}
