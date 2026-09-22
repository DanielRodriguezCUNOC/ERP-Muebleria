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
public class ProductoCatalogoResponseDTO {
    private Long id;
    private String sku;
    private String nombre;
    private String descripcion;
    private String categoria;
    private BigDecimal precioVenta;
    private Integer existenciaTotal;
    private Boolean activo;
}