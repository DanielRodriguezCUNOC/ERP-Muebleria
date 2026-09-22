package com.erp.muebleria.modules.administracion.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.administracion.infrastructure.persistence.entities.BitacoraOperacionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de Spring Data JPA para la entidad BitacoraOperacionJpaEntity.
 * Proporciona métodos para realizar operaciones CRUD y consultas personalizadas en la tabla bitacora_operacion de la base de datos.
 */
@Repository
public interface SpringDataBitacoraRepository extends JpaRepository<BitacoraOperacionJpaEntity, Long> {

    List<BitacoraOperacionJpaEntity> findByEmpleadoIdOrderByFechaDesc(Long empleadoId);
}
