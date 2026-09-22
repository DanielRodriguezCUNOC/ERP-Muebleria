package com.erp.muebleria.modules.inventario.application.useCases;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoDTO;
import com.erp.muebleria.modules.inventario.domain.ports.InventarioRepositoryPort;
import com.erp.muebleria.modules.inventario.infrastructure.persistence.mappers.InventarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConsultarExistenciasActualesUseCase {

    private final InventarioRepositoryPort inventarioRepositoryPort;
    private final InventarioMapper inventarioMapper;

    @Transactional(readOnly = true)
    public Page<ExistenciaProductoDTO> ejecutar(String busqueda, Boolean soloBajoStock, Pageable pageable) {
        return inventarioRepositoryPort.buscarExistencias(busqueda, soloBajoStock, pageable)
                .map(inventarioMapper::toExistenciaDto);
    }
}