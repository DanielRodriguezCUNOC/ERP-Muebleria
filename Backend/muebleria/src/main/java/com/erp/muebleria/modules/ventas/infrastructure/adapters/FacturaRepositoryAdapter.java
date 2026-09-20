package com.erp.muebleria.modules.ventas.infrastructure.adapters;

import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaRepositoryPort;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.FacturaJpaEntity;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.mappers.VentaMapper;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.repositories.SpringDataFacturaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class FacturaRepositoryAdapter implements FacturaRepositoryPort {

    private final SpringDataFacturaRepository facturaRepository;
    private final VentaMapper ventaMapper;

    @Override
    public Optional<Factura> buscarPorId(Long id) {
        return facturaRepository.findById(id)
                .map(ventaMapper::toFacturaDomain);
    }

    @Override
    public Optional<Factura> buscarPorNumero(String numeroFactura) {
        return facturaRepository.findByNumeroFactura(numeroFactura)
                .map(ventaMapper::toFacturaDomain);
    }

    @Override
    public Factura guardarFactura(Factura factura) {
        FacturaJpaEntity entity = ventaMapper.toFacturaEntity(factura);
        FacturaJpaEntity savedEntity = facturaRepository.save(entity);
        return ventaMapper.toFacturaDomain(savedEntity);
    }
}
