package com.erp.muebleria.modules.inventario.application.dto;

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
public class LoteExistenciaDTO {
    private Long loteId;
    private Integer cantidadInicial;
    private Integer cantidadDisponible;
    private BigDecimal costoUnitario;
    private LocalDateTime fechaIngreso;
}