package com.erp.muebleria.modules.ventas.infrastructure.adapters;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.domain.ports.VentaRepositoryPort;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.FacturaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.VentaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.mappers.VentaMapper;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.repositories.SpringDataFacturaRepository;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.repositories.SpringDataVentaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class VentaRepositoryAdapter implements VentaRepositoryPort {

    private final SpringDataVentaRepository ventaRepository;
    private final SpringDataFacturaRepository facturaRepository;
    private final VentaMapper mapper;

    @Override
    public Venta guardarVentaConFactura(Venta venta, Factura factura) {
        VentaJpaEntity ventaEntity = mapper.toEntity(venta);
        ventaEntity.getDetalles().forEach(detalle -> detalle.setVenta(ventaEntity));

        VentaJpaEntity savedVenta = ventaRepository.save(ventaEntity);

        FacturaJpaEntity facturaEntity = mapper.toFacturaEntity(factura);
        facturaEntity.setSaleId(savedVenta.getId());
        FacturaJpaEntity savedFactura = facturaRepository.save(facturaEntity);

        Venta domainResult = mapper.toDomain(savedVenta);
        domainResult.setFactura(mapper.toFacturaDomain(savedFactura));
        return domainResult;
    }

    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Factura> buscarFacturaPorVentaId(Long ventaId) {
        return facturaRepository.findBySaleId(ventaId).map(mapper::toFacturaDomain);
    }
}