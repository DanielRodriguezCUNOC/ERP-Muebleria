package com.erp.muebleria.modules.compras.infrastructure.controllers;

import com.erp.muebleria.modules.compras.application.dto.CambiarEstadoProveedorRequestDTO;
import com.erp.muebleria.modules.compras.application.dto.ModificarProveedorRequestDTO;
import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.RegistrarProveedorRequestDTO;
import com.erp.muebleria.modules.compras.application.useCases.CambiarEstadoProveedorUseCase;
import com.erp.muebleria.modules.compras.application.useCases.ConsultarProveedoresUseCase;
import com.erp.muebleria.modules.compras.application.useCases.ModificarProveedorUseCase;
import com.erp.muebleria.modules.compras.application.useCases.RegistrarProveedorUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Proveedores", description = "Gestión de proveedores")
@RequestMapping("/api/v1/compras/proveedores")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ProveedorController {

    private final RegistrarProveedorUseCase registrarProveedorUseCase;
    private final ModificarProveedorUseCase modificarProveedorUseCase;
    private final CambiarEstadoProveedorUseCase cambiarEstadoProveedorUseCase;
    private final ConsultarProveedoresUseCase consultarProveedoresUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Registrar proveedor", description = "Permite registrar un nuevo proveedor en el sistema.")
    public ResponseEntity<ProveedorResponseDTO> registrarProveedor(@RequestBody RegistrarProveedorRequestDTO request) {
        ProveedorResponseDTO response = registrarProveedorUseCase.ejecutar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Modificar proveedor", description = "Permite modificar los datos de un proveedor existente en el sistema.")
    public ResponseEntity<ProveedorResponseDTO> modificarProveedor(
            @PathVariable Long id,
            @RequestBody ModificarProveedorRequestDTO request) {
        ProveedorResponseDTO response = modificarProveedorUseCase.ejecutar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Cambiar estado de proveedor", description = "Permite cambiar el estado (activo/inactivo) de un proveedor existente en el sistema.")
    public ResponseEntity<ProveedorResponseDTO> cambiarEstadoProveedor(
            @PathVariable Long id,
            @RequestBody CambiarEstadoProveedorRequestDTO request) {
        ProveedorResponseDTO response = cambiarEstadoProveedorUseCase.ejecutar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('COMPRAS_VER', 'REPORTES_VER', 'COMPRAS_GESTIONAR')")
    @Operation(summary = "Consultar catálogo de proveedores", description = "Permite listar proveedores con filtro opcional por nombre.")
    public ResponseEntity<List<ProveedorResponseDTO>> consultarProveedores(
            @RequestParam(required = false) String nombre) {
        List<ProveedorResponseDTO> proveedores = consultarProveedoresUseCase.ejecutar(nombre);
        return ResponseEntity.ok(proveedores);
    }
}
