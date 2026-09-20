package com.erp.muebleria.modules.ventas.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.ventas.application.dto.DetalleVentaRequestDTO;
import com.erp.muebleria.modules.ventas.application.dto.RegistrarVentaRequestDTO;
import com.erp.muebleria.modules.ventas.application.dto.VentaResponseDTO;
import com.erp.muebleria.modules.ventas.domain.entities.DetalleVenta;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaPdfPort;
import com.erp.muebleria.modules.ventas.domain.ports.VentaRepositoryPort;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.mappers.VentaMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RegistrarVentaUseCase {

    private final VentaRepositoryPort ventaRepositoryPort;
    private final FacturaPdfPort facturaPdfPort;
    private final VentaMapper ventaMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public VentaResponseDTO ejecutar(RegistrarVentaRequestDTO request) {
        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new ReglaNegocioException("La venta debe incluir al menos un producto");
        }

        BigDecimal subTotal = BigDecimal.ZERO;
        for (DetalleVentaRequestDTO item : request.getDetalles()) {
            if (item.getCantidad() == null || item.getCantidad() <= 0) {
                throw new ReglaNegocioException("La cantidad debe ser mayor a cero");
            }
            if (item.getPrecioUnitario() == null || item.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
                throw new ReglaNegocioException("El precio unitario no puede ser negativo");
            }
            BigDecimal costoLinea = item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad()));
            subTotal = subTotal.add(costoLinea);
        }

        BigDecimal iva = subTotal.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = subTotal.add(iva).setScale(2, RoundingMode.HALF_UP);

        Venta venta = new Venta();
        venta.setEmpleadoId(request.getEmpleadoId());
        venta.setClienteId(request.getClienteId());
        venta.setFechaVenta(LocalDateTime.now());
        venta.setSubTotal(subTotal);
        venta.setIva(iva);
        venta.setTotal(total);

        venta.setDetalles(request.getDetalles().stream().map(dto -> {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setProductoId(dto.getProductoId());
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
            return detalle;
        }).collect(Collectors.toList()));

        Factura factura = new Factura();
        factura.setNumeroFactura("FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        factura.setFechaEmision(LocalDateTime.now());
        factura.setClienteNit(request.getClienteNit() != null ? request.getClienteNit() : "CF");
        factura.setClienteNombre(request.getClienteNombre() != null ? request.getClienteNombre() : "Consumidor Final");
        factura.setEstado("EMITIDA");

        Venta ventaGuardada = ventaRepositoryPort.guardarVentaConFactura(venta, factura);
        Factura facturaGuardada = ventaGuardada.getFactura();

        facturaPdfPort.generarPdf(ventaGuardada, facturaGuardada);

        String detalleBitacora = String.format("Venta ID: %d registrada con Factura N°: %s por un total de Q%.2f",
                ventaGuardada.getId(), facturaGuardada.getNumeroFactura(), ventaGuardada.getTotal());

        eventPublisher.publishEvent(new OperacionRealizadaEvent(
                request.getEmpleadoId(),
                "REGISTRAR_VENTA",
                "VENTAS",
                detalleBitacora
        ));

        return ventaMapper.toResponseDto(ventaGuardada, facturaGuardada, "Venta y Factura registradas exitosamente");
    }
}