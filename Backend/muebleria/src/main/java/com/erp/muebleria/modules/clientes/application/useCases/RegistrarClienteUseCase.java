package com.erp.muebleria.modules.clientes.application.useCases;



import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.clientes.infrastructure.persistence.mappers.ClienteMapper;
import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.clientes.application.dto.RegistrarClienteRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrarClienteUseCase {

    private final ClienteRepositoryPort clienteRepositoryPort;
    private final ClienteMapper clienteMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ClienteResponseDTO ejecutar(RegistrarClienteRequestDTO request) {
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new ReglaNegocioException("El nombre del cliente es obligatorio");
        }
        if (request.getNit() == null || request.getNit().isBlank()) {
            throw new ReglaNegocioException("El NIT del cliente es obligatorio");
        }

        String nitLimpio = request.getNit().trim().toUpperCase();

        if (!"CF".equals(nitLimpio) && clienteRepositoryPort.existePorNit(nitLimpio)) {
            throw new ReglaNegocioException("Ya existe un cliente registrado con el NIT: " + nitLimpio);
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre().trim());
        cliente.setNit(nitLimpio);
        cliente.setDireccion(request.getDireccion());
        cliente.setTelefono(request.getTelefono());
        cliente.setActivo(true);

        Cliente clienteGuardado = clienteRepositoryPort.guardar(cliente);

        String detalleBitacora = String.format("Cliente registrado: %s (NIT: %s, ID: %d)",
                clienteGuardado.getNombre(), clienteGuardado.getNit(), clienteGuardado.getId());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "REGISTRAR_CLIENTE",
                "VENTAS",
                detalleBitacora
        ));

        return clienteMapper.toResponseDto(clienteGuardado, "Cliente registrado exitosamente");
    }
}