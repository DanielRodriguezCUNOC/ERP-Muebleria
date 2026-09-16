package com.erp.muebleria.modules.administracion.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarConfiguracionDTO {

    private Double tasaIva;
    private String metodoValoracion;
    private String resolucionFactura;
    private String serieFacturas;
    private Long correlativoSiguiente;
}
