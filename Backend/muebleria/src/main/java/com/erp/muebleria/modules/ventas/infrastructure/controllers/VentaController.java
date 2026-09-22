package com.erp.muebleria.modules.ventas.infrastructure.controllers;

import com.erp.muebleria.modules.common.domain.exceptions.RecursoNoEncontradoException;
import com.erp.muebleria.modules.ventas.application.dto.RegistrarVentaRequestDTO;
import com.erp.muebleria.modules.ventas.application.dto.VentaResponseDTO;
import com.erp.muebleria.modules.ventas.application.useCases.RegistrarVentaUseCase;
import com.erp.muebleria.modules.ventas.domain.entities.Factura;
import com.erp.muebleria.modules.ventas.domain.entities.Venta;
import com.erp.muebleria.modules.ventas.domain.ports.FacturaPdfPort;
import com.erp.muebleria.modules.ventas.domain.ports.VentaRepositoryPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Ventas", description = "Gestión de ventas")
@RequestMapping("/api/v1/ventas")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class VentaController {

    private final RegistrarVentaUseCase registrarVentaUseCase;
    private final VentaRepositoryPort ventaRepositoryPort;
    private final FacturaPdfPort facturaPdfPort;

    @PostMapping
    @PreAuthorize("hasAuthority('VENTAS_GESTIONAR')")
    @Operation(summary = "Registrar venta", description = "Permite registrar una nueva venta.")
    public ResponseEntity<VentaResponseDTO> registrarVenta(@RequestBody RegistrarVentaRequestDTO request) {
        VentaResponseDTO response = registrarVentaUseCase.ejecutar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/factura/pdf")
    @PreAuthorize("hasAuthority('VENTAS_VER')")
    @Operation(summary = "Descargar factura en PDF", description = "Permite descargar la factura asociada a una venta en formato PDF.")
    public ResponseEntity<byte[]> descargarFacturaPdf(@PathVariable Long id) {
        Venta venta = ventaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la venta con ID: " + id));

        Factura factura = ventaRepositoryPort.buscarFacturaPorVentaId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la factura asociada a la venta ID: " + id));

        byte[] pdf = facturaPdfPort.generarPdf(venta, factura);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "Factura-" + factura.getNumeroFactura() + ".pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
