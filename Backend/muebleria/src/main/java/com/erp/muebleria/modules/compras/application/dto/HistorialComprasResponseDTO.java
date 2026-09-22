package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistorialComprasResponseDTO {
    private Long id;
    private LocalDateTime fechaCompra;
    private Long empleadoId;
    private Integer totalProductos;
    private BigDecimal costoTotal;
    private String proveedores;
}