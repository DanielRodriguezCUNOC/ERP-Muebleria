package com.erp.muebleria.modules.compras.application.useCases;

import com.erp.muebleria.modules.compras.application.dto.FiltroHistorialCompraDTO;
import com.erp.muebleria.modules.compras.application.dto.HistorialComprasResponseDTO;
import com.erp.muebleria.modules.compras.domain.ports.CompraRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsultarHistorialComprasUseCase {

    private final CompraRepositoryPort compraRepositoryPort;

    @Transactional(readOnly = true)
    public List<HistorialComprasResponseDTO> ejecutar(FiltroHistorialCompraDTO filtro) {
        return compraRepositoryPort.consultarHistorial(filtro);
    }
}