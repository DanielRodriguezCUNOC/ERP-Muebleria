package com.erp.muebleria.modules.compras.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.ProveedorJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataProveedorRepository extends JpaRepository<ProveedorJpaEntity, Long> {

		@Query(value = """
						SELECT id, nombre, direccion, telefono_contacto, activo
						FROM proveedor
						WHERE activo = TRUE
							AND (:nombre IS NULL OR LOWER(nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
						ORDER BY nombre ASC
						""", nativeQuery = true)
		List<ProveedorJpaEntity> buscarTodos(@Param("nombre") String nombre);
}
