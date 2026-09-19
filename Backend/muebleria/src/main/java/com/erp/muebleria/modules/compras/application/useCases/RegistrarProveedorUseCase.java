package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.RegistrarProveedorRequestDTO;
import com.erp.muebleria.modules.compras.domain.entities.Proveedor;
import com.erp.muebleria.modules.compras.domain.ports.ProveedorRepositoryPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.ProveedorMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistrarProveedorUseCase {

    private final ProveedorRepositoryPort proveedorRepositoryPort;
    private final ProveedorMapper proveedorMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ProveedorResponseDTO ejecutar(RegistrarProveedorRequestDTO request){

        if (request.getNombre() == null || request.getNombre().isEmpty())
            throw new IllegalArgumentException("El nombre del proveedor es obligatorio");

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(request.getNombre());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setTelefonoContacto(request.getTelefonoContacto());
        proveedor.setActivo(true);

        Proveedor proveedorGuardado = proveedorRepositoryPort.guardar(proveedor);

        String detalleBitacora = String.format("Se registró el proveedor '%s' con ID: %d",
                proveedorGuardado.getNombre(), proveedorGuardado.getId());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "REGISTRAR_PROVEEDOR",
                "COMPRAS",
                detalleBitacora
        ));

        return proveedorMapper.toDto(proveedorGuardado);
    }
}
