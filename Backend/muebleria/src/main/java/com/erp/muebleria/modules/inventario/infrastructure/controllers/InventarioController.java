package com.erp.muebleria.modules.inventario.infrastructure.controllers;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoDTO;
import com.erp.muebleria.modules.inventario.application.useCases.ConsultarExistenciasActualesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Inventario", description = "Gestión de inventario")
@RequestMapping("/api/v1/inventario")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class InventarioController {

    private final ConsultarExistenciasActualesUseCase consultarExistenciasActualesUseCase;

    @GetMapping("/existencias")
    @PreAuthorize("hasAnyAuthority('INVENTARIO_VER', 'VENTAS_VER')")
    @Operation(summary = "Consultar existencias de productos", description = "Permite consultar las existencias actuales de los productos con filtros opcionales y paginación.")
    public ResponseEntity<Page<ExistenciaProductoDTO>> consultarExistencias(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false, defaultValue = "false") Boolean soloBajoStock,
            @PageableDefault(size = 20, sort = "nombre") Pageable pageable) {

        Page<ExistenciaProductoDTO> response = consultarExistenciasActualesUseCase.ejecutar(busqueda, soloBajoStock, pageable);
        return ResponseEntity.ok(response);
    }
}