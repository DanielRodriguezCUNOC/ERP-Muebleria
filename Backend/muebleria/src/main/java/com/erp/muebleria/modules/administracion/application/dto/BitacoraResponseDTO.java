package com.erp.muebleria.modules.administracion.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BitacoraResponseDTO {

    private Long id;
    private String empleadoId;
    private String accion;
    private String modulo;
    private String detalle;
    private LocalDateTime fecha;
}
