package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.compras.application.dto.ModificarProveedorRequestDTO;
import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.domain.ports.ProveedorRepositoryPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.ProveedorMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ModificarProveedorUseCase {

    private final ProveedorRepositoryPort proveedorRepositoryPort;
    private final ProveedorMapper proveedorMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ProveedorResponseDTO ejecutar(Long id, ModificarProveedorRequestDTO request) {
        Proveedor proveedor = proveedorRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el proveedor con ID: " + id));

        if (request.getNombre() != null && !request.getNombre().isBlank()) {
            proveedor.setNombre(request.getNombre());
        }
        if (request.getDireccion() != null) {
            proveedor.setDireccion(request.getDireccion());
        }
        if (request.getTelefonoContacto() != null) {
            proveedor.setTelefonoContacto(request.getTelefonoContacto());
        }
        if (request.getActivo() != null) {
            proveedor.setActivo(request.getActivo());
        }

        Proveedor proveedorActualizado = proveedorRepositoryPort.guardar(proveedor);

        String detalleBitacora = String.format("Se modificó la información del proveedor ID: %d (%s)",
                proveedorActualizado.getId(), proveedorActualizado.getNombre());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "MODIFICAR_PROVEEDOR",
                "COMPRAS",
                detalleBitacora
        ));

        return proveedorMapper.toDto(proveedorActualizado);
    }
}