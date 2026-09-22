package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.MovimientoProducto;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor
public class GenerarHistorialMovimientosProductoUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public Page<MovimientoProducto> ejecutar(Long productoId, Pageable pageable) {
        return reportesPort.obtenerMovimientosPorProducto(productoId, pageable);
    }
}
