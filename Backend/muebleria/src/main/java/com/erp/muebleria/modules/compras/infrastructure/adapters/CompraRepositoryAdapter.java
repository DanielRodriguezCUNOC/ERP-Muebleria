package com.erp.muebleria.modules.compras.infrastructure.adapters;

import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.LoteJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.CompraMapper;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataCompraRepository;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataLoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CompraRepositoryAdapter implements CompraRepositoryPort {

    private final SpringDataCompraRepository compraRepository;
    private final SpringDataLoteRepository loteRepository;

    @Override
    public Compra guardarCompra(Compra compra) {
        //* Convertir a entidad JPA y guardar para obtener IDs
        CompraJpaEntity compraEntity = CompraMapper.toEntity(compra);
        CompraJpaEntity savedCompra = compraRepository.save(compraEntity);

        //* Generar Lotes para cada detalle guardado y persistirlos
        List<LoteJpaEntity> lotes = savedCompra.getDetalles().stream().map(detalle -> {
            LoteJpaEntity lote = new LoteJpaEntity();
            lote.setDetalleCompraId(savedCompra.getId());
            lote.setProductId(detalle.getId().getProductId());
            lote.setCreadoEn(LocalDateTime.now());
            lote.setCostoUnitario(detalle.getPrecioUnitario());
            lote.setCantidadInicial(detalle.getCantidad());
            lote.setCantidadDisponible(detalle.getCantidad());
            return lote;
        }).collect(Collectors.toList());

        loteRepository.saveAll(lotes);

        //* Retornar el modelo de dominio
        return CompraMapper.toDomain(savedCompra);
    }
}