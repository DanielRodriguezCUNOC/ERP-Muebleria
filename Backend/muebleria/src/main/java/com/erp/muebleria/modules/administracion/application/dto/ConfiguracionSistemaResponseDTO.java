package com.erp.muebleria.modules.administracion.application.dto;

import com.erp.muebleria.modules.reportes.domain.entities.MetodoValoracion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConfiguracionSistemaResponseDTO {

    private Long id;
    private BigDecimal tasaIva;
    private MetodoValoracion metodoValoracion;
    private String resolucionFacturas;
    private String serieFacturas;
    private Long correlativoSiguiente;
}