package com.erp.muebleria.modules.administracion.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.administracion.infrastructure.persistence.entities.ConfiguracionSistemaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Spring Data JPA para la entidad ConfiguracionSistemaJpaEntity.
 * Proporciona métodos CRUD para interactuar con la base de datos.
 */
@Repository
public interface SpringDataConfiguracionRepository extends JpaRepository<ConfiguracionSistemaJpaEntity, Long> {
}
