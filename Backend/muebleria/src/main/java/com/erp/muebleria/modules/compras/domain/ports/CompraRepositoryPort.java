package com.erp.muebleria.modules.compras.domain.ports;

import com.erp.muebleria.modules.compras.application.dto.FiltroHistorialCompraDTO;
import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.ProductoBajoStockResponseDTO;
import com.erp.muebleria.modules.compras.domain.entities.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraRepositoryPort {

    Compra guardarCompra(Compra compra);
    Optional<Compra> buscarPorId(Long id);
    void eliminarCompra(Long id);
    boolean validarLotesSinConsumir(Long compraId);
    org.springframework.data.domain.Page<HistorialComprasResponseDTO> consultarHistorial(FiltroHistorialCompraDTO filtro, org.springframework.data.domain.Pageable pageable);
    List<ProductoBajoStockResponseDTO> consultarProductosBajoStock();
}
