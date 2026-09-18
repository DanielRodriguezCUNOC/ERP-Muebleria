package com.erp.muebleria.modules.compras.infrastructure.controllers;

import com.erp.muebleria.modules.compras.application.dto.CompraResponseDTO;
import com.erp.muebleria.modules.compras.application.dto.RegistrarCompraRequestDTO;
import com.erp.muebleria.modules.compras.application.useCases.RegistrarCompraUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Compras", description = "Gestión de compras")
@RequestMapping("api/v1/compras")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class CompraController {

    private final RegistrarCompraUseCase registrarCompraUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('COMPRAS_GESTIONAR')")
    @Operation(summary = "Registrar compra", description = "Permite registrar una nueva compra en el sistema.")
    public ResponseEntity<CompraResponseDTO> registrarCompra(@RequestBody RegistrarCompraRequestDTO request){
        CompraResponseDTO response = registrarCompraUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
