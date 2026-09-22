package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.ProductoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductoRepository extends JpaRepository<ProductoJpaEntity, Long> {
}
