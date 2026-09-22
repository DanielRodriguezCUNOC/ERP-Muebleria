package com.erp.muebleria.modules.compras.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroHistorialCompraDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate fechaInicio;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate fechaFin;
    private Long proveedorId;
    private Long empleadoId;
}