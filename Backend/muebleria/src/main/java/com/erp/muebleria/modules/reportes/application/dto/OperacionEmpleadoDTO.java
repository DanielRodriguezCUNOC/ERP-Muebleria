package com.erp.muebleria.modules.reportes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperacionEmpleadoDTO {

    private Long id;
    private Long empleadoId;
    private String accion;
    private String modulo;
    private String detalle;
    private LocalDateTime fecha;
}
