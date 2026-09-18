package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponseDTO {

    private Long id;
    private Long proveedorId;
    private Long empleadoId;
    private LocalDateTime fechaCompra;
    private String mensaje;
}
