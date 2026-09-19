package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProveedorRepository extends JpaRepository<ProveedorJpaEntity, Long> {
}
