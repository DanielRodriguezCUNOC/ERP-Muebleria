package com.erp.muebleria.modules.ventas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleVentaRequestDTO {
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}