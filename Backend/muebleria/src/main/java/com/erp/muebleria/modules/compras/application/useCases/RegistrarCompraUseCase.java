package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.compras.application.dto.CompraResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.RegistrarCompraRequestDTO;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.entities.DetalleCompra;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import com.erp.muebleria.modules.compras.domain.ports.InventarioModuloPort;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegistrarCompraUseCase {

    private final CompraRepositoryPort compraRepositoryPort;
    private final InventarioModuloPort inventarioModuloPort;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public CompraResponseDTO ejecutar(RegistrarCompraRequestDTO request) {

        //* Construir entidad de dominio Compra
        Compra compra = new Compra();
        compra.setEmpleadoId(request.getEmpleadoId());
        compra.setFechaCompra(LocalDateTime.now());

        //* Construir Detalles y calcular costos totales
        List<DetalleCompra> detalles = request.getDetalles().stream().map(dto -> {
            DetalleCompra detalle = new DetalleCompra();
            detalle.setProductoId(dto.getProductoId());
            detalle.setProveedorId(dto.getProveedorId());
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());

            BigDecimal costoTotal = dto.getPrecioUnitario().multiply(new BigDecimal(dto.getCantidad()));
            detalle.setCostoTotal(costoTotal);
            return detalle;
        }).collect(Collectors.toList());

        Set<Long> proveedorIds = detalles.stream()
                .map(DetalleCompra::getProveedorId)
                .collect(Collectors.toSet());

        compra.setDetalles(detalles);
        compra.setProveedorIds(proveedorIds);

        //* Persistir Compra
        Compra compraGuardada = compraRepositoryPort.guardarCompra(compra);

        //* Actualizar Inventario y Log
        inventarioModuloPort.registrarEnInventario(compraGuardada);

        //* Publicar evento para la bitácora
        String detalleBitacora = String.format("Se registró la compra ID: %d para %d proveedor(es) con %d productos",
                compraGuardada.getId(), proveedorIds.size(), request.getDetalles().size());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "REGISTRAR_COMPRA",
                "COMPRAS",
                detalleBitacora
        ));

        //* Retornar DTO
        return new CompraResponseDTO(
                compraGuardada.getId(),
                new ArrayList<>(compraGuardada.getProveedorIds()),
                compraGuardada.getEmpleadoId(),
                compraGuardada.getFechaCompra(),
                "Compra registrada exitosamente"
        );
    }


}
