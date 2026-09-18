package com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataRolRepository extends JpaRepository<RolJpaEntity, Long> {

    @Modifying
    @Query(value = "DELETE FROM rol_permiso WHERE rol_id = :rolId AND permiso_id = :permisoId", nativeQuery = true)
    void eliminarPermisoDeRol(@Param("rolId") Long rolId, @Param("permisoId") Long permisoId);


}
