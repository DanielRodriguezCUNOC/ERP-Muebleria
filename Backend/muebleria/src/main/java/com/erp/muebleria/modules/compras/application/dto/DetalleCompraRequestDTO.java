package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompraRequestDTO {

    private Long productoId;
    private Long proveedorId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
