package com.erp.muebleria.modules.administracion.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad que representa la bitácora de operaciones del sistema.
 */
@Getter
@Setter
@AllArgsConstructor
public class BitacoraOperacion {

    private Long id;
    private String empleadoId;
    private String accion;
    private String modulo;
    private String detalle;
    private LocalDateTime fecha;
}
