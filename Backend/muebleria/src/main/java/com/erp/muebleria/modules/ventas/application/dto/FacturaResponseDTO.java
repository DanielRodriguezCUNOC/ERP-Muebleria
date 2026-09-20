package com.erp.muebleria.modules.ventas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaResponseDTO {
    private Long id;
    private Long saleId;
    private String numeroFactura;
    private LocalDateTime fechaEmision;
    private String clienteNit;
    private String clienteNombre;
    private String estado;
    private String mensaje;
}