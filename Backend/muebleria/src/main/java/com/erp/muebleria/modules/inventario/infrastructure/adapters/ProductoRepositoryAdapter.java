package com.erp.muebleria.modules.inventario.infrastructure.adapters;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoResponseDTO;
import com.erp.muebleria.modules.inventario.application.dto.FiltroCatalogoProductoDTO;
import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;
import com.erp.muebleria.modules.inventario.domain.ports.ProductoRepositoryPort;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.extractors.ExistenciaProductoExtractor;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers.ProductoCatalogoMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductoCatalogoMapper productoCatalogoMapper;
    private final ExistenciaProductoExtractor existenciaProductoExtractor;
    @Override
    public List<ProductoCatalogoResponseDTO> consultarCatalogo(FiltroCatalogoProductoDTO filtro) {
        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.sku, p.nombre, p.descripcion, p.categoria, p.precio_venta, p.activo, " +
                        "COALESCE(i.existencia, 0) AS existencia_total " +
                        "FROM producto p " +
                        "LEFT JOIN inventario i ON p.id = i.producto_id " +
                        "WHERE 1=1 "
        );

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filtro.getNombre() != null && !filtro.getNombre().isBlank()) {
            sql.append("AND LOWER(p.nombre) LIKE LOWER(:nombre) ");
            params.addValue("nombre", "%" + filtro.getNombre().trim() + "%");
        }

        if (filtro.getCategoria() != null && !filtro.getCategoria().isBlank()) {
            sql.append("AND LOWER(p.categoria) = LOWER(:categoria) ");
            params.addValue("categoria", filtro.getCategoria().trim());
        }

        if (filtro.getActivo() != null) {
            sql.append("AND p.activo = :activo ");
            params.addValue("activo", filtro.getActivo());
        }

        sql.append("ORDER BY p.nombre ASC");

        return jdbcTemplate.query(sql.toString(), params, productoCatalogoMapper);
    }

    @Override
    public Optional<ExistenciaProductoResponseDTO> obtenerExistenciasPorProductoId(Long productoId) {
        String sql =
                "SELECT p.id AS producto_id, p.sku, p.nombre, p.existencia_minima, " +
                        "COALESCE(i.existencia, 0) AS existencia_total, " +
                        "l.id AS lote_id, l.cantidad_inicial, l.cantidad_disponible, l.costo_unitario, l.creado_en " +
                        "FROM producto p " +
                        "LEFT JOIN inventario i ON p.id = i.producto_id " +
                        "LEFT JOIN lote l ON p.id = l.product_id AND l.cantidad_disponible > 0 " +
                        "WHERE p.id = :productoId " +
                        "ORDER BY l.creado_en ASC";

        MapSqlParameterSource params = new MapSqlParameterSource("productoId", productoId);
        ExistenciaProductoResponseDTO resultado = jdbcTemplate.query(sql, params, existenciaProductoExtractor);

        return Optional.ofNullable(resultado);
    }
}
