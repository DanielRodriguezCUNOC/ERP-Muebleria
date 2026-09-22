package com.erp.muebleria.modules.ventas.application.dto;

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
public class FacturaDetalleResponseDTO {
    private Long id;
    private Long saleId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private String clienteNit;
    private String clienteNombre;
    private String estado;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles = new ArrayList<>();
}