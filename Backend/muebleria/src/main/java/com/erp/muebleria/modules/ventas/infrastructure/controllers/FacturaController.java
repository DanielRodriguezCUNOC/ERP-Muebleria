package com.erp.muebleria.modules.ventas.infrastructure.controllers;

import com.erp.muebleria.modules.ventas.application.dto.AnularFacturaRequestDTO;
import com.erp.muebleria.modules.ventas.application.dto.FacturaDetalleResponseDTO;
import com.erp.muebleria.modules.ventas.application.dto.FacturaResponseDTO;
import com.erp.muebleria.modules.ventas.application.useCases.AnularFacturaUseCase;
import com.erp.muebleria.modules.ventas.application.useCases.ConsultarFacturaPorNumeroUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Factura", description = "Gestión de facturas")
@RequestMapping("api/v1/ventas/facturas")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class FacturaController {

    private AnularFacturaUseCase anularFacturaUseCase;
    private final ConsultarFacturaPorNumeroUseCase consultarFacturaPorNumeroUseCase;

    @PatchMapping("/{id}/anular")
    @PreAuthorize("hasAuthority('VENTAS_GESTIONAR')")
    @Operation(summary = "Anular factura", description = "Permite anular una factura existente.")
    public ResponseEntity<FacturaResponseDTO> anularFactura(
            @PathVariable Long id,
            @RequestBody AnularFacturaRequestDTO request) {
        FacturaResponseDTO response = anularFacturaUseCase.ejecutar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/numero/{numeroFactura}")
    @PreAuthorize("hasAuthority('VENTAS_VER')")
    public ResponseEntity<FacturaDetalleResponseDTO> consultarPorNumero(@PathVariable String numeroFactura) {
        FacturaDetalleResponseDTO response = consultarFacturaPorNumeroUseCase.ejecutar(numeroFactura);
        return ResponseEntity.ok(response);
    }
}
