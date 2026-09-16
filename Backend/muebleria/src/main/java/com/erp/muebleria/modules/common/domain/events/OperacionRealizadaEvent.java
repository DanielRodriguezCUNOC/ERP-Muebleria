package com.erp.muebleria.modules.common.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Evento que representa una operación realizada en el sistema.
 * Contiene información sobre el empleado que realizó la operación, la acción realizada, el módulo en el que se realizó y detalles adicionales.
 */
@Getter
@AllArgsConstructor
public class OperacionRealizadaEvent {

    private final Long empleadoId;
    private final String accion;
    private final String modulo;
    private final String detalle;


}
