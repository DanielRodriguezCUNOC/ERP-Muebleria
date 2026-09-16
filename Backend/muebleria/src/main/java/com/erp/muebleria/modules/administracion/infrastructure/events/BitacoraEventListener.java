package com.erp.muebleria.modules.administracion.infrastructure.events;

import com.erp.muebleria.modules.administracion.domain.entities.BitacoraOperacion;
import com.erp.muebleria.modules.administracion.domain.ports.BitacoraOperacionRepositoryPort;
import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Escucha eventos de operaciones realizadas y registra la información en la bitácora.
 * Este listener se activa cuando se publica un evento de tipo OperacionRealizadaEvent.
 */
@Component
@AllArgsConstructor
public class BitacoraEventListener {

    private final BitacoraOperacionRepositoryPort repositoryPort;

    @EventListener
    public void registrarBitacora (OperacionRealizadaEvent event){

        BitacoraOperacion operacion = new BitacoraOperacion(
                null,
                event.getEmpleadoId().toString(),
                event.getAccion(),
                event.getModulo(),
                event.getDetalle(),
                LocalDateTime.now()
        );
        repositoryPort.guardar(operacion);
    }
}
