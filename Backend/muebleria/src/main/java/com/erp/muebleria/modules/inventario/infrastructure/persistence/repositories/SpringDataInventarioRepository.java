package com.erp.muebleria.modules.inventario.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.ProductoJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataInventarioRepository extends JpaRepository<ProductoJpaEntity, Long> {

    @Query(value = """
        SELECT p FROM ProductoJpaEntity p 
        JOIN FETCH p.inventario i
        WHERE p.activo = true
          AND (:busqueda IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) 
               OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :busqueda, '%'))
               OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :busqueda, '%')))
          AND (:soloBajoStock IS FALSE OR i.existencia <= p.existenciaMinima)
        """,
            countQuery = """
        SELECT COUNT(p) FROM ProductoJpaEntity p 
        JOIN p.inventario i
        WHERE p.activo = true
          AND (:busqueda IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) 
               OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :busqueda, '%'))
               OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :busqueda, '%')))
          AND (:soloBajoStock IS FALSE OR i.existencia <= p.existenciaMinima)
        """)
    Page<ProductoJpaEntity> buscarExistencias(
            @Param("busqueda") String busqueda,
            @Param("soloBajoStock") Boolean soloBajoStock,
            Pageable pageable
    );
}
