package com.erp.muebleria.modules.administracion.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfiguracionSistema {

    private Long id;
    private Double tasaIva;
    private MetodoValoracion metodoValoracion;
    //* Numero de autorización
    private String resolucionFacturas;
    private String serieFacturas;
    //* El número que se asigna a la próxima venta
    private Long correlativoSiguiente;

    public void avanzarCorrelativoFactura() {
        this.correlativoSiguiente++;
    }
}
