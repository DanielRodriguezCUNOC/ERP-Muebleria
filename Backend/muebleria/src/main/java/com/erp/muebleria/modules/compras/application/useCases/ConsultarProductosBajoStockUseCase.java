package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.compras.application.dto.ProductoBajoStockResponseDTO;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Aclaracion por el nombre: Este caso es para ver los productos que tienen un stock bajo
 * Es que puede confundirse con productos que estan en stock, pero no es lo mismo XD.
 * No es lo mismo apesta a traste que a traste apesta :3
 */
@Service
@AllArgsConstructor
public class ConsultarProductosBajoStockUseCase {

    private final CompraRepositoryPort compraRepositoryPort;

    @Transactional(readOnly = true)
    public List<ProductoBajoStockResponseDTO> ejecutar() {
        return compraRepositoryPort.consultarProductosBajoStock();
    }
}
