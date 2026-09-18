package com.erp.muebleria.modules.clientes.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.clientes.infrastructure.persistence.entities.ClienteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataClienteRepository extends JpaRepository<ClienteJpaEntity, Long> {
    Optional<ClienteJpaEntity> findByNit(String nit);
    boolean existsByNit(String nit);
}