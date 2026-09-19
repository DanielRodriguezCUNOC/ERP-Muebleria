package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoBajoStockResponseDTO {
    private Long productoId;
    private String sku;
    private String nombre;
    private String categoria;
    private Integer existenciaActual;
    private Integer existenciaMinima;
    private Integer cantidadSugeridaReabastecimiento;
}