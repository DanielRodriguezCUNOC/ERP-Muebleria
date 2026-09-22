package com.erp.muebleria.modules.inventario.domain.ports;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoResponseDTO;
import com.erp.muebleria.modules.inventario.application.dto.FiltroCatalogoProductoDTO;
import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    List<ProductoCatalogoResponseDTO> consultarCatalogo(FiltroCatalogoProductoDTO filtro);
    Optional<ExistenciaProductoResponseDTO> obtenerExistenciasPorProductoId(Long productoId);
}
