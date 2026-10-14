package com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataPermisoRepository extends JpaRepository<PermisoJpaEntity, Long> {

    @Query("""
            SELECT new com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO(
                p.id,
                p.nombre,
                p.descripcion
            )
            FROM UsuarioJpaEntity u
            JOIN u.rol r
            JOIN r.permisos p
            WHERE u.id = :usuarioId
            """)
    List<PermisoResponseDTO> obtenerPermisosPorUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("""
            SELECT new com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO(
                p.id,
                p.nombre,
                p.descripcion
            )
            FROM PermisoJpaEntity p
            """)
    List<PermisoResponseDTO> getAllPermisos();
}
