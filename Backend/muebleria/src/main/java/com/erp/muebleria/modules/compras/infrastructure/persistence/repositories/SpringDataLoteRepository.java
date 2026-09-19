package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.LoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataLoteRepository extends JpaRepository<LoteJpaEntity, Long> {
    List<LoteJpaEntity> findByDetalleCompraId(Long detalleCompraId);

    void deleteByDetalleCompraId(Long detalleCompraId);
}
