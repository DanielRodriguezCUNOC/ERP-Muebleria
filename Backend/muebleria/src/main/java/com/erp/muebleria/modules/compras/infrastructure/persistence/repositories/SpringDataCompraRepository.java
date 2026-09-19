package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCompraRepository extends JpaRepository<CompraJpaEntity, Long> {

}
