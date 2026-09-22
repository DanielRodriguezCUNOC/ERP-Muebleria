package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.compras.application.dto.AnularCompraRequestDTO;
import com.erp.muebleria.modules.compras.application.dto.CompraResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import com.erp.muebleria.modules.compras.domain.ports.InventarioModuloPort;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AnularCompraUseCase {

    private final CompraRepositoryPort compraRepositoryPort;
    private final InventarioModuloPort inventarioModuloPort;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public CompraResponseDTO ejecutar(AnularCompraRequestDTO request) {

        //* Validar la existencia de la compra
        Optional<Compra> compraOptional = compraRepositoryPort.buscarPorId(request.getCompraId());
        if (compraOptional.isEmpty())
            throw new RecursoNoEncontradoException("No se encontró la compra con ID: " + request.getCompraId());

        Compra compra = compraOptional.get();

        //* Verificar que el inventario o lote no se haya vendido.
        boolean loteIntactos = compraRepositoryPort.validarLotesSinConsumir(request.getCompraId());
        if (!loteIntactos)
            throw new IllegalStateException("No se puede anular la compra, ya que los lotes ya han sido consumidos.");

        //* Revertir el inventario ingresado
        inventarioModuloPort.registrarReversionInventario(compra);

        //* Eliminar la compra y sus referencias
        compraRepositoryPort.eliminarCompra(request.getCompraId());

        //* Registrar la accion en la bitacora
        String detalleBitacora = String.format("Se anuló la compra ID: %d. Motivo: %s", request.getCompraId(), request.getMotivo());
        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "ANULAR_COMPRA",
                "COMPRAS",
                detalleBitacora
        ));

        //* Generar DTO de respuesta
        return toResponseDto(compra, "Compra anulada exitosamente");
    }

    private CompraResponseDTO toResponseDto(Compra compra, String mensaje) {
        return new CompraResponseDTO(
                compra.getId(),
                compra.getProveedorIds() != null ? compra.getProveedorIds().stream().toList() : java.util.List.of(),
                compra.getEmpleadoId(),
                compra.getFechaCompra(),
                mensaje
        );
    }
}
