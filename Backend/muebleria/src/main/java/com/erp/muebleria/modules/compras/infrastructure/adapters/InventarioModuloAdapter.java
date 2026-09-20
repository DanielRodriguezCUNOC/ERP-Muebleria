package com.erp.muebleria.modules.compras.infrastructure.adapters;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.compras.domain.entities.Compra;
import com.erp.muebleria.modules.compras.domain.entities.DetalleCompra;
import com.erp.muebleria.modules.compras.domain.ports.InventarioModuloPort;
import com.erp.muebleria.modules.compras.infrastructure.persistence.entities.LoteJpaEntity;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataInventarioJpaRepository;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataLoteRepository;
import com.erp.muebleria.modules.compras.infrastructure.persistence.repositories.SpringDataProductoRepository;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.InventarioJpaEntity;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.entities.ProductoJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class InventarioModuloAdapter implements InventarioModuloPort {
    private final SpringDataInventarioJpaRepository inventarioRepository;
    private final SpringDataLoteRepository loteRepository;
    private final SpringDataProductoRepository productoRepository;

    @Override
    @Transactional
    public void registrarEnInventario(Object object) {
        if (!(object instanceof Compra compra)) {
            throw new IllegalArgumentException("El objeto provisto debe ser una instancia de Compra");
        }

        for (DetalleCompra detalle : compra.getDetalles()) {
            //* Aumentar el stock general en la tabla 'inventario'
            InventarioJpaEntity inventario = inventarioRepository.findByProductoId(detalle.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró registro de inventario para el producto ID: " + detalle.getProductoId()));

            inventario.setExistencia(inventario.getExistencia() + detalle.getCantidad());
            inventarioRepository.save(inventario);

            //* Registrar el nuevo lote correspondiente a esta compra
            ProductoJpaEntity productoRef = productoRepository.findById(detalle.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró el producto ID: " + detalle.getProductoId()));

            LoteJpaEntity lote = LoteJpaEntity.builder()
                    .detalleCompraId(detalle.getCompraId() != null ? detalle.getCompraId() : compra.getId())
                    .productoId(productoRef.getId())
                    .costoUnitario(detalle.getPrecioUnitario())
                    .cantidadInicial(detalle.getCantidad())
                    .cantidadDisponible(detalle.getCantidad())
                    .creadoEn(LocalDateTime.now())
                    .build();

            loteRepository.save(lote);
        }
    }

    @Override
    @Transactional
    public void registrarReversionInventario(Compra compra) {
        for (DetalleCompra detalle : compra.getDetalles()) {
            //* Descontar las existencias agregadas previamente
            InventarioJpaEntity inventario = inventarioRepository.findByProductoId(detalle.getProductoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "No se encontró registro de inventario para el producto ID: " + detalle.getProductoId()));

            int nuevaExistencia = inventario.getExistencia() - detalle.getCantidad();
            if (nuevaExistencia < 0) {
                throw new ReglaNegocioException(
                        "No es posible anular la compra. El inventario actual sería negativo para el producto ID: " + detalle.getProductoId());
            }

            inventario.setExistencia(nuevaExistencia);
            inventarioRepository.save(inventario);

            //* Eliminar o inactivar el lote generado por este detalle de compra
            Long detalleId = detalle.getCompraId() != null ? detalle.getCompraId() : compra.getId();
            loteRepository.deleteByDetalleCompraId(detalleId);
        }
    }
}
