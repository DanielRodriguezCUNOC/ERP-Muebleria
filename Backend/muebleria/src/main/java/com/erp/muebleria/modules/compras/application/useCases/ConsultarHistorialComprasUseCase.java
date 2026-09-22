package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.compras.application.dto.FiltroHistorialCompraDTO;
import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConsultarHistorialComprasUseCase {

    private final CompraRepositoryPort compraRepositoryPort;

    @Transactional(readOnly = true)
    public Page<HistorialComprasResponseDTO> ejecutar(FiltroHistorialCompraDTO filtro, Pageable pageable) {
        // * Validación opcional de rango de fechas
        if (filtro.getFechaInicio() != null && filtro.getFechaFin() != null
                && filtro.getFechaInicio().isAfter(filtro.getFechaFin())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha fin.");
        }
        return compraRepositoryPort.consultarHistorial(filtro, pageable);
    }
}