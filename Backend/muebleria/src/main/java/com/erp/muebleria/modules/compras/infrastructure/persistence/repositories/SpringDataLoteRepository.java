package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.LoteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataLoteRepository extends JpaRepository<LoteJpaEntity, Long> {
}
