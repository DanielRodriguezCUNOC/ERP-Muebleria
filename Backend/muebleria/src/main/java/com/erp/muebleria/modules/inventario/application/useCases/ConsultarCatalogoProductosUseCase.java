package com.erp.muebleria.modules.inventario.application.useCases;

import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.inventario.application.dto.FiltroCatalogoProductoDTO;
import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;
import com.erp.muebleria.modules.inventario.domain.ports.ProductoRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultarCatalogoProductosUseCase {

    private final ProductoRepositoryPort productoRepositoryPort;

    @Transactional(readOnly = true)
    public List<ProductoCatalogoResponseDTO> ejecutar(FiltroCatalogoProductoDTO filtro) {
        if (filtro.getNombre() != null && filtro.getNombre().trim().length() < 2 && !filtro.getNombre().isBlank()) {
            throw new ReglaNegocioException("El filtro de nombre debe contener al menos 2 caracteres");
        }

        return productoRepositoryPort.consultarCatalogo(filtro);
    }
}
