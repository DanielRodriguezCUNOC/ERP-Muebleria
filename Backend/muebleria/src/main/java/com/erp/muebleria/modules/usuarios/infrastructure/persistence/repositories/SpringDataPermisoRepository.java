package com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.PermisoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataPermisoRepository extends JpaRepository<PermisoJpaEntity, Long> {

    @Query(value = """
            SELECT DISTINCT p.id, p.codigo, p.descripcion 
            FROM permiso p
            JOIN rol_permiso rp ON p.id = rp.permiso_id
            JOIN empleado e ON e.rol_id = rp.rol_id
            WHERE e.id = :usuarioId
            """, nativeQuery = true)
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
