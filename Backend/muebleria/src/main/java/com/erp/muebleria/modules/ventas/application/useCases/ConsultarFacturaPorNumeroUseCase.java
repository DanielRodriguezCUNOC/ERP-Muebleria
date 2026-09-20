package com.erp.muebleria.modules.ventas.application.useCases;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.ventas.application.dto.FacturaDetalleResponseDTO;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaRepositoryPort;
import com.erp.muebleria.modules.ventas.domain.ports.VentaRepositoryPort;
import com.erp.muebleria.modules.ventas.infrastructure.persistence.mappers.VentaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ConsultarFacturaPorNumeroUseCase {

    private final FacturaRepositoryPort facturaRepositoryPort;
    private final VentaRepositoryPort ventaRepositoryPort;
    private final VentaMapper ventaMapper;

    @Transactional(readOnly = true)
    public FacturaDetalleResponseDTO ejecutar(String numeroFactura) {
        if (numeroFactura == null || numeroFactura.isBlank()) {
            throw new ReglaNegocioException("El número de factura es requerido");
        }

        String numeroLimpio = numeroFactura.trim().toUpperCase();

        Factura factura = facturaRepositoryPort.buscarPorNumero(numeroLimpio)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la factura N°: " + numeroLimpio));

        Venta venta = ventaRepositoryPort.buscarPorId(factura.getSaleId())
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la venta asociada a la factura ID: " + factura.getId()));

        return ventaMapper.toFacturaDetalleResponseDto(factura, venta);
    }
}