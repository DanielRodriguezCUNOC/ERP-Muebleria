package com.erp.muebleria.modules.clientes.application.useCases;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.application.dto.ModificarClienteRequestDTO;
import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ModificarClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final com.erp.muebleria.modules.clientes.application.mappers.ClienteApplicationMapper clienteMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ClienteResponseDTO ejecutar(Long clienteId, ModificarClienteRequestDTO request) {
        Cliente clienteExistente = clienteRepositoryPort.buscarPorId(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el cliente con ID: " + clienteId));

        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new ReglaNegocioException("El nombre del cliente es obligatorio");
        }
        if (request.getNit() == null || request.getNit().isBlank()) {
            throw new ReglaNegocioException("El NIT del cliente es obligatorio");
        }

        String nuevoNit = request.getNit().trim().toUpperCase();

        // Validar duplicidad de NIT solo si fue modificado y no es 'CF'
        if (!nuevoNit.equalsIgnoreCase(clienteExistente.getNit()) && !"CF".equals(nuevoNit)) {
            if (clienteRepositoryPort.existePorNit(nuevoNit)) {
                throw new ReglaNegocioException("Ya existe otro cliente registrado con el NIT: " + nuevoNit);
            }
        }

        clienteExistente.setNombre(request.getNombre().trim());
        clienteExistente.setNit(nuevoNit);
        clienteExistente.setDireccion(request.getDireccion());
        clienteExistente.setTelefono(request.getTelefono());

        if (request.getActivo() != null) {
            clienteExistente.setActivo(request.getActivo());
        }

        Cliente clienteActualizado = clienteRepositoryPort.guardar(clienteExistente);

        String detalleBitacora = String.format("Cliente actualizado: %s (NIT: %s, ID: %d)",
                clienteActualizado.getNombre(), clienteActualizado.getNit(), clienteActualizado.getId());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "MODIFICAR_CLIENTE",
                "VENTAS",
                detalleBitacora
        ));

        return clienteMapper.toResponseDto(clienteActualizado, "Cliente actualizado exitosamente");
    }
}