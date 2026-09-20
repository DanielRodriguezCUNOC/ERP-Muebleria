package com.erp.muebleria.modules.ventas.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.FacturaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataFacturaRepository extends JpaRepository<FacturaJpaEntity, Long> {
    Optional<FacturaJpaEntity> findBySaleId(Long saleId);
    Optional<FacturaJpaEntity> findByNumeroFactura(String numeroFactura);
}