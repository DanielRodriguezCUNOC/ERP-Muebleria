package com.erp.muebleria.modules.ventas.infrastructure.persistence.mappers;

import com.erp.muebleria.modules.ventas.application.dto.DetalleVentaResponseDTO;
import com.erp.muebleria.modules.ventas.application.dto.FacturaDetalleResponseDTO;
import com.erp.muebleria.modules.ventas.application.dto.FacturaResponseDTO;
import com.erp.muebleria.modules.ventas.application.dto.VentaResponseDTO;
import com.erp.muebleria.modules.ventas.domain.entities.DetalleVenta;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.DetalleVentaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.FacturaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.VentaJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VentaMapper {

    @Mapping(target = "detalles", source = "detalles")
    VentaJpaEntity toEntity(Venta domain);

    @Mapping(target = "detalles", source = "detalles")
    Venta toDomain(VentaJpaEntity entity);

    @Mapping(target = "ventaId", source = "id.ventaId")
    @Mapping(target = "productoId", source = "id.productoId")
    DetalleVenta toDomainDetail(DetalleVentaJpaEntity entity);

    @Mapping(target = "id.ventaId", source = "ventaId")
    @Mapping(target = "id.productoId", source = "productoId")
    @Mapping(target = "venta", ignore = true)
    DetalleVentaJpaEntity toEntityDetail(DetalleVenta domain);

    FacturaJpaEntity toFacturaEntity(Factura domain);

    Factura toFacturaDomain(FacturaJpaEntity entity);

    default VentaResponseDTO toResponseDto(Venta venta, Factura factura, String mensaje) {
        return new VentaResponseDTO(
                venta.getId(),
                factura != null ? factura.getId() : null,
                factura != null ? factura.getNumeroFactura() : null,
                venta.getSubTotal(),
                venta.getIva(),
                venta.getTotal(),
                venta.getFechaVenta(),
                mensaje
        );
    }

    default FacturaResponseDTO toFacturaResponseDto(Factura domain, String mensaje) {
        return new FacturaResponseDTO(
                domain.getId(),
                domain.getSaleId(),
                domain.getNumeroFactura(),
                domain.getFechaEmision(),
                domain.getClienteNit(),
                domain.getClienteNombre(),
                domain.getEstado(),
                mensaje
        );
    }

    default FacturaDetalleResponseDTO toFacturaDetalleResponseDto(Factura factura, Venta venta) {
        List<DetalleVentaResponseDTO> detallesDto = venta.getDetalles().stream().map(d ->
                new DetalleVentaResponseDTO(
                        d.getProductoId(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getPrecioUnitario().multiply(java.math.BigDecimal.valueOf(d.getCantidad()))
                )
        ).collect(Collectors.toList());

        return new FacturaDetalleResponseDTO(
                factura.getId(),
                factura.getSaleId(),
                factura.getNumeroFactura(),
                factura.getFechaEmision(),
                factura.getClienteNit(),
                factura.getClienteNombre(),
                factura.getEstado(),
                venta.getSubTotal(),
                venta.getIva(),
                venta.getTotal(),
                detallesDto
        );
    }
}