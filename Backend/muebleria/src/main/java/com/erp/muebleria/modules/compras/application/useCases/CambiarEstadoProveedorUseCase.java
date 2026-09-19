package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.compras.application.dto.CambiarEstadoProveedorRequestDTO;
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
public class CambiarEstadoProveedorUseCase {

    private final ProveedorRepositoryPort proveedorRepositoryPort;
    private final ProveedorMapper proveedorMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ProveedorResponseDTO ejecutar(Long id, CambiarEstadoProveedorRequestDTO request) {
        if (request.getActivo() == null) {
            throw new IllegalArgumentException("El parámetro 'activo' es obligatorio");
        }

        Proveedor proveedor = proveedorRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el proveedor con ID: " + id));

        proveedor.setActivo(request.getActivo());
        Proveedor proveedorActualizado = proveedorRepositoryPort.guardar(proveedor);

        String accion = request.getActivo() ? "ACTIVAR_PROVEEDOR" : "DESACTIVAR_PROVEEDOR";
        String estadoTexto = request.getActivo() ? "activó" : "desactivó";
        String detalleBitacora = String.format("Se %s al proveedor ID: %d (%s)",
                estadoTexto, proveedorActualizado.getId(), proveedorActualizado.getNombre());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                accion,
                "COMPRAS",
                detalleBitacora
        ));

        return proveedorMapper.toDto(proveedorActualizado);
    }
}