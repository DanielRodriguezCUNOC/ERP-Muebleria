package com.erp.muebleria.modules.ventas.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private Long id;
    private Long empleadoId;
    private Long clienteId;
    private LocalDateTime fechaVenta;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal total;
    private List<DetalleVenta> detalles = new ArrayList<>();
    private Factura factura;
}