package com.erp.muebleria.modules.compras.infrastructure.controllers;

import com.erp.muebleria.modules.compras.application.dto.*;
import com.erp.muebleria.modules.compras.application.useCases.AnularCompraUseCase;
import com.erp.muebleria.modules.compras.application.useCases.ConsultarHistorialComprasUseCase;
import com.erp.muebleria.modules.compras.application.useCases.ConsultarProductosBajoStockUseCase;
import com.erp.muebleria.modules.compras.application.useCases.RegistrarCompraUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Compras", description = "Gestión de compras")
@RequestMapping("/api/v1/compras")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class CompraController {

    private final RegistrarCompraUseCase registrarCompraUseCase;
    private final AnularCompraUseCase anularCompraUseCase;
    private final ConsultarHistorialComprasUseCase consultarHistorialComprasUseCase;
    private final ConsultarProductosBajoStockUseCase consultarProductosBajoStockUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Registrar compra", description = "Permite registrar una nueva compra en el sistema.")
    public ResponseEntity<CompraResponseDTO> registrarCompra(@RequestBody RegistrarCompraRequestDTO request) {
        CompraResponseDTO response = registrarCompraUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Anular compra", description = "Permite anular una compra existente en el sistema.")
    public ResponseEntity<CompraResponseDTO> anularCompra(
            @RequestBody AnularCompraRequestDTO request) {
        CompraResponseDTO response = anularCompraUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COMPRAS_VER')")
    @Operation(summary = "Consultar historial de compras", description = "Permite consultar el historial de compras aplicando filtros opcionales.")
    public ResponseEntity<Page<HistorialComprasResponseDTO>> consultarHistorial(
            @Valid FiltroHistorialCompraDTO filtro, Pageable pageable) {

        Page<HistorialComprasResponseDTO> historial = consultarHistorialComprasUseCase.ejecutar(filtro, pageable);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/productos-bajo-stock")
    @PreAuthorize("hasAnyAuthority('COMPRAS_VER', 'INVENTARIO_VER')")
    @Operation(summary = "Consultar productos bajo stock", description = "Permite consultar los productos que están por debajo del stock mínimo definido.")
    public ResponseEntity<List<ProductoBajoStockResponseDTO>> consultarProductosBajoStock() {
        List<ProductoBajoStockResponseDTO> productos = consultarProductosBajoStockUseCase.ejecutar();
        return ResponseEntity.ok(productos);
    }
}
