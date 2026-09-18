package com.erp.muebleria.modules.reportes.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ConsultaVentasPeriodoDTO {
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
