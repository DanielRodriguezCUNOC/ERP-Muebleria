package com.erp.muebleria.modules.ventas.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrarVentaRequestDTO {
    private Long empleadoId;
    private Long clienteId;
    private String clienteNit;
    private String clienteNombre;
    private List<DetalleVentaRequestDTO> detalles;
}