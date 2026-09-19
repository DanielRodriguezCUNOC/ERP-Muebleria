package com.erp.muebleria.modules.compras.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCompra {
    private Long compraId;
    private Long productoId;
    private Long proveedorId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal costoTotal;
}
