package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.InventarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataInventarioJpaRepository extends JpaRepository<InventarioJpaEntity, Long> {
    Optional<InventarioJpaEntity> findByProductoId(Long productoId);
}