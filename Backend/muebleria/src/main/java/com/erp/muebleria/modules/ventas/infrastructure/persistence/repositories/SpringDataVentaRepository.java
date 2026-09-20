package com.erp.muebleria.modules.ventas.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.ventas.infrastructure.persistence.entities.VentaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataVentaRepository extends JpaRepository<VentaJpaEntity, Long> {
}