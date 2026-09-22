package com.erp.muebleria.modules.ventas.application.mappers;

import com.erp.muebleria.modules.ventas.application.dto.FacturaResponseDTO;
import com.erp.muebleria.modules.ventas.application.dto.VentaResponseDTO;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.FacturaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.VentaJpaEntity;

public interface VentaApplicationMapper {
    VentaJpaEntity toEntity(Venta domain);

    Venta toDomain(VentaJpaEntity entity);

    FacturaJpaEntity toFacturaEntity(Factura domain);

    Factura toFacturaDomain(FacturaJpaEntity entity);

    VentaResponseDTO toResponseDto(Venta venta, Factura factura, String mensaje);

    FacturaResponseDTO toFacturaResponseDto(Factura domain, String mensaje);
}
