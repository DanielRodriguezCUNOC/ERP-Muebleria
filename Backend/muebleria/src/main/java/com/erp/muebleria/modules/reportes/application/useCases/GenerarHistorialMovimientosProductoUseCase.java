package com.erp.muebleria.modules.reportes.application.useCases;

import com.erp.muebleria.modules.reportes.domain.models.MovimientoProducto;
import com.erp.muebleria.modules.reportes.domain.ports.ReportesGerencialesRepositoryPort;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GenerarHistorialMovimientosProductoUseCase {

    private final ReportesGerencialesRepositoryPort reportesPort;

    public List<MovimientoProducto> ejecutar(Long productoId) {
        return reportesPort.obtenerMovimientosPorProducto(productoId);
    }
}
