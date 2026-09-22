package com.erp.muebleria.modules.inventario.domain.entities;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductoInventario {

    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Boolean activo;
    private String sku;
    private Integer existenciaMinima;
    private Integer existencia;

    // Reglas de negocio del dominio
    public boolean esBajoStock() {
        return this.existencia != null && this.existenciaMinima != null
                && this.existencia <= this.existenciaMinima;
    }

    public boolean estaAgotado() {
        return this.existencia == null || this.existencia <= 0;
    }

    public boolean tieneStockSuficiente(int cantidadRequerida) {
        return this.existencia != null && this.existencia >= cantidadRequerida;
    }
}