package com.erp.muebleria.modules.administracion.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Mapea directamente los campos hacia la tabla configuracion_sistema de la base de datos.
 */

@Entity
@Table (name = "configuracion_sistema")
@Getter
@Setter
public class ConfiguracionSistemaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tasa_iva", nullable = false, precision = 5, scale = 4)
    private BigDecimal tasaIva;

    @Column(name = "metodo_valoracion", nullable = false)
    private String metodoValoracion;

    @Column(name = "resolucion_facturas")
    private String resolucionFacturas;

    @Column(name = "serie_facturas")
    private String serieFacturas;

    @Column(name = "correlativo_siguiente", nullable = false)
    private Long correlativoSiguiente;
}
