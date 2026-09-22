package com.erp.muebleria.modules.inventario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExistenciaProductoDTO {
    private Long productoId;
    private String sku;
    private String nombre;
    private String categoria;
    private Integer existencia;
    private Integer existenciaMinima;
    private BigDecimal precio;
    private String estadoStock;
}