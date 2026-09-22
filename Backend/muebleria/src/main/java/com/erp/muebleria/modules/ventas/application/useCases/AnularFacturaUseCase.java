package com.erp.muebleria.modules.ventas.application.useCases;

import com.erp.muebleria.modules.common.domain.events.OperacionRealizadaEvent;
import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.common.domain.exceptions.ReglaNegocioException;
import com.erp.muebleria.modules.ventas.application.dto.AnularFacturaRequestDTO;
import com.erp.muebleria.modules.ventas.application.dto.FacturaResponseDTO;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AnularFacturaUseCase {

        private final FacturaRepositoryPort facturaRepositoryPort;
        private final com.erp.muebleria.modules.ventas.application.mappers.VentaApplicationMapper ventaMapper;
        private final ApplicationEventPublisher eventPublisher;

        @Transactional
        public FacturaResponseDTO ejecutar(Long facturaId, AnularFacturaRequestDTO request) {
                Factura factura = facturaRepositoryPort.buscarPorId(facturaId)
                                .orElseThrow(() -> new RecursoNoEncontradoException(
                                                "No se encontró la factura con ID: " + facturaId));

                if ("ANULADA".equalsIgnoreCase(factura.getEstado())) {
                        throw new ReglaNegocioException(
                                        "La factura N° " + factura.getNumeroFactura() + " ya se encuentra anulada");
                }

                factura.setEstado("ANULADA");
                Factura facturaActualizada = facturaRepositoryPort.guardarFactura(factura);

                String motivoTexto = (request.getMotivo() != null && !request.getMotivo().isBlank())
                                ? request.getMotivo()
                                : "Sin motivo especificado";

                String detalleBitacora = String.format("Se anuló la factura N° %s (ID: %d). Motivo: %s",
                                facturaActualizada.getNumeroFactura(), facturaActualizada.getId(), motivoTexto);

                eventPublisher.publishEvent(new OperacionRealizadaEvent(
                                request.getEmpleadoId(),
                                "ANULAR_FACTURA",
                                "VENTAS",
                                detalleBitacora));

                return ventaMapper.toFacturaResponseDto(facturaActualizada, "Factura anulada correctamente");
        }
}