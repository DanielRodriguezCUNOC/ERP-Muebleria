package com.erp.muebleria.modules.inventario.application.useCases;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoResponseDTO;
import com.erp.muebleria.modules.inventario.domain.ports.ProductoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConsultarExistenciasProductoUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    @Transactional(readOnly = true)
    public ExistenciaProductoResponseDTO ejecutar(Long productoId) {
        return productoRepositoryPort.obtenerExistenciasPorProductoId(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el producto con ID: " + productoId));
    }
}