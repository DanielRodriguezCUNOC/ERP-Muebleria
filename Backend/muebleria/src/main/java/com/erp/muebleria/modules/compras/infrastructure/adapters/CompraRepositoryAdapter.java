package com.erp.muebleria.modules.compras.infrastructure.adapters;

import com.erp.muebleria.modules.compras.application.dto.FiltroHistorialCompraDTO;
import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.ProductoBajoStockResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.CompraJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.LoteJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.CompraMapper;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.HistorialCompraMapper;
import com.erp.muebleria.modules.compras.infrastructure.persistence.mappers.ProductoBajoStockMapper;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataCompraRepository;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataLoteRepository;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CompraRepositoryAdapter implements CompraRepositoryPort {

    private final SpringDataCompraRepository compraRepository;
    private final CompraMapper compraMapper;
    private final SpringDataLoteRepository loteRepository;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final HistorialCompraMapper historialCompraMapper;
    private final ProductoBajoStockMapper productoBajoStockMapper;

    @Override
    public Compra guardarCompra(Compra compra) {
        // * Convertir a entidad JPA y guardar para obtener IDs
        CompraJpaEntity compraEntity = compraMapper.toEntity(compra);
        CompraJpaEntity savedCompra = compraRepository.save(compraEntity);

        // * Generar Lotes para cada detalle guardado y persistirlos
        List<LoteJpaEntity> lotes = savedCompra.getDetalles().stream().map(detalle -> {
            LoteJpaEntity lote = new LoteJpaEntity();
            lote.setDetalleCompraId(savedCompra.getId());
            lote.setProductoId(detalle.getId().getProductoId());
            lote.setCreadoEn(LocalDateTime.now());
            lote.setCostoUnitario(detalle.getPrecioUnitario());
            lote.setCantidadInicial(detalle.getCantidad());
            lote.setCantidadDisponible(detalle.getCantidad());
            return lote;
        }).collect(Collectors.toList());

        loteRepository.saveAll(lotes);

        // * Retornar el modelo de dominio
        return compraMapper.toDomain(savedCompra);
    }

    @Override
    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id).map(compraMapper::toDomain);
    }

    @Override
    public void eliminarCompra(Long id) {
        loteRepository.deleteByDetalleCompraId(id);
        compraRepository.deleteById(id);
    }

    @Override
    public boolean validarLotesSinConsumir(Long compraId) {
        List<LoteJpaEntity> lotes = loteRepository.findByDetalleCompraId(compraId);
        return lotes.stream().allMatch(lote -> lote.getCantidadDisponible().equals(lote.getCantidadInicial()));
    }

    @Override
    public org.springframework.data.domain.Page<HistorialComprasResponseDTO> consultarHistorial(
            FiltroHistorialCompraDTO filtro, Pageable pageable) {
        StringBuilder sql = new StringBuilder(
                "SELECT c.id, c.fecha_compra, c.empleado_id, " +
                        "COALESCE(SUM(dc.cantidad), 0) AS total_productos, " +
                        "COALESCE(SUM(dc.costo_total), 0) AS costo_total, " +
                        "STRING_AGG(DISTINCT p.nombre, ', ') AS proveedores " +
                        "FROM compra c " +
                        "LEFT JOIN detalle_compra dc ON c.id = dc.compra_id " +
                        "LEFT JOIN proveedor p ON dc.proveedor_id = p.id " +
                        "WHERE 1=1 ");

        MapSqlParameterSource params = new MapSqlParameterSource();

        if (filtro.getFechaInicio() != null) {
            sql.append("AND c.fecha_compra >= :fechaInicio ");
            params.addValue("fechaInicio", filtro.getFechaInicio().atStartOfDay());
        }

        if (filtro.getFechaFin() != null) {
            sql.append("AND c.fecha_compra <= :fechaFin ");
            params.addValue("fechaFin", filtro.getFechaFin().atTime(LocalTime.MAX));
        }

        if (filtro.getEmpleadoId() != null) {
            sql.append("AND c.empleado_id = :empleadoId ");
            params.addValue("empleadoId", filtro.getEmpleadoId());
        }

        if (filtro.getProveedorId() != null) {
            sql.append(
                    "AND EXISTS (SELECT 1 FROM detalle_compra dc2 WHERE dc2.compra_id = c.id AND dc2.proveedor_id = :proveedorId) ");
            params.addValue("proveedorId", filtro.getProveedorId());
        }

        sql.append("GROUP BY c.id, c.fecha_compra, c.empleado_id ");
        sql.append("ORDER BY c.fecha_compra DESC");

        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();
        String pagedSql = sql.toString() + " LIMIT " + pageSize + " OFFSET " + offset;

        List<HistorialComprasResponseDTO> content = jdbcTemplate.query(pagedSql, params, historialCompraMapper);

        // Count total
        String countSql = "SELECT COUNT(DISTINCT c.id) FROM compra c LEFT JOIN detalle_compra dc ON c.id = dc.compra_id LEFT JOIN proveedor p ON dc.proveedor_id = p.id WHERE 1=1 ";
        StringBuilder countSqlBuilder = new StringBuilder(countSql);
        if (filtro.getFechaInicio() != null) {
            countSqlBuilder.append("AND c.fecha_compra >= :fechaInicio ");
        }
        if (filtro.getFechaFin() != null) {
            countSqlBuilder.append("AND c.fecha_compra <= :fechaFin ");
        }
        if (filtro.getEmpleadoId() != null) {
            countSqlBuilder.append("AND c.empleado_id = :empleadoId ");
        }
        if (filtro.getProveedorId() != null) {
            countSqlBuilder.append(
                    "AND EXISTS (SELECT 1 FROM detalle_compra dc2 WHERE dc2.compra_id = c.id AND dc2.proveedor_id = :proveedorId) ");
        }

        long total = jdbcTemplate.queryForObject(countSqlBuilder.toString(), params, Long.class);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<ProductoBajoStockResponseDTO> consultarProductosBajoStock() {
        String sql = "SELECT p.id AS producto_id, p.sku, p.nombre, p.categoria, " +
                "COALESCE(i.existencia, 0) AS existencia_actual, " +
                "p.existencia_minima, " +
                "GREATEST(0, p.existencia_minima - COALESCE(i.existencia, 0)) AS cantidad_sugerida " +
                "FROM producto p " +
                "LEFT JOIN inventario i ON p.id = i.producto_id " +
                "WHERE p.activo = TRUE " +
                " AND COALESCE(i.existencia, 0) <= p.existencia_minima " +
                " ORDER BY (p.existencia_minima - COALESCE(i.existencia, 0)) DESC, p.nombre ASC";
        return jdbcTemplate.query(sql, productoBajoStockMapper);
    }

}