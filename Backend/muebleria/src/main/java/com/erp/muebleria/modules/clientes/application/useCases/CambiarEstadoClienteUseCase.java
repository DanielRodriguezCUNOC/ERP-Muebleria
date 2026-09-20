package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.CambiarEstadoClienteRequestDTO;
import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.mappers.ClienteMapper;
import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CambiarEstadoClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ClienteMapper clienteMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ClienteResponseDTO ejecutar(Long clienteId, CambiarEstadoClienteRequestDTO request) {
        if (request.getActivo() == null) {
            throw new ReglaNegocioException("El estado 'activo' es obligatorio");
        }

        Cliente cliente = clienteRepositoryPort.buscarPorId(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con ID: " + clienteId));

        if (cliente.getActivo().equals(request.getActivo())) {
            String estadoTexto = Boolean.TRUE.equals(cliente.getActivo()) ? "activo" : "inactivo";
            throw new ReglaNegocioException("El cliente ya se encuentra en estado " + estadoTexto);
        }

        cliente.setActivo(request.getActivo());
        Cliente clienteActualizado = clienteRepositoryPort.guardar(cliente);

        String accion = Boolean.TRUE.equals(request.getActivo()) ? "ACTIVAR_CLIENTE" : "DESACTIVAR_CLIENTE";
        String estadoTexto = Boolean.TRUE.equals(request.getActivo()) ? "activado" : "desactivado";

        String detalleBitacora = String.format("Cliente ID %d (%s, NIT: %s) fue %s",
                clienteActualizado.getId(), clienteActualizado.getNombre(), clienteActualizado.getNit(), estadoTexto);

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                accion,
                "VENTAS",
                detalleBitacora
        ));

        return clienteMapper.toResponseDto(clienteActualizado, "Estado del cliente actualizado a " + estadoTexto + " exitosamente");
    }
}