package com.erp.muebleria.modules.ventas.application.dto;

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
public class VentaResponseDTO {
    private Long ventaId;
    private Long facturaId;
    private String numeroFactura;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal total;
    private LocalDateTime fechaVenta;
    private String mensaje;
}