package com.erp.muebleria.modules.inventario.infrastructure.controllers;

import com.erp.muebleria.modules.inventario.application.dto.ExistenciaProductoResponseDTO;
import com.erp.muebleria.modules.inventario.application.dto.FiltroCatalogoProductoDTO;
import com.erp.muebleria.modules.inventario.application.dto.ProductoCatalogoResponseDTO;
import com.erp.muebleria.modules.inventario.application.useCases.ConsultarCatalogoProductosUseCase;
import com.erp.muebleria.modules.inventario.application.useCases.ConsultarExistenciasProductoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Productos", description = "Gestión de productos")
@RequestMapping("/api/v1/inventario/productos")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ProductoController {

    private final ConsultarCatalogoProductosUseCase consultarCatalogoProductosUseCase;
    private final ConsultarExistenciasProductoUseCase consultarExistenciasProductoUseCase;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('INVENTARIO_VER', 'COMPRAS_VER', 'VENTAS_VER')")
    @Operation(summary = "Consultar catálogo de productos", description = "Permite consultar el catálogo de productos con filtros opcionales.")
    public ResponseEntity<List<ProductoCatalogoResponseDTO>> consultarCatalogo(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Boolean activo) {

        FiltroCatalogoProductoDTO filtro = new FiltroCatalogoProductoDTO(nombre, categoria, activo);
        List<ProductoCatalogoResponseDTO> productos = consultarCatalogoProductosUseCase.ejecutar(filtro);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}/existencias")
    @PreAuthorize("hasAnyAuthority('INVENTARIO_VER','COMPRAS_VER', 'VENTAS_VER')")
    @Operation(summary = "Consultar existencias de un producto", description = "Permite consultar las existencias de un producto específico por su ID.")
    public ResponseEntity<ExistenciaProductoResponseDTO> consultarExistencias(@PathVariable Long id) {
        ExistenciaProductoResponseDTO response = consultarExistenciasProductoUseCase.ejecutar(id);
        return ResponseEntity.ok(response);
    }
}
