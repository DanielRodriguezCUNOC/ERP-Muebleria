package com.erp.muebleria.modules.administracion.infrastructure.controllers;

import com.erp.muebleria.modules.administracion.application.dto.ActualizarConfiguracionDTO;
import com.erp.muebleria.modules.administracion.application.dto.ConfiguracionSistemaResponseDTO;
import com.erp.muebleria.modules.administracion.application.useCases.ActualizarConfiguracionUseCase;
import com.erp.muebleria.modules.administracion.application.useCases.ObtenerConfiguracionUseCase;
import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/admin/configuracion")
@Tag(name = "Configuración", description = "Gestión de la configuración del sistema")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ConfiguracionController {

    private final ObtenerConfiguracionUseCase obtenerConfiguracionUseCase;
    private final ActualizarConfiguracionUseCase actualizarConfiguracionUseCase;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    @Operation(summary = "Obtener Configuración", description = "Obtiene los parámetros globales de IVA, facturación y método de valoración")
    public ResponseEntity<ConfiguracionSistemaResponseDTO> obtenerConfiguracion() {
        return ResponseEntity.ok(obtenerConfiguracionUseCase.ejecutar());
    }

    @PutMapping
    @PreAuthorize("hasAuthority('CONFIGURACION_GESTIONAR')")
    public ResponseEntity<ConfiguracionSistema> actualizarConfiguracion(@RequestBody ActualizarConfiguracionDTO dto) {
        ConfiguracionSistema configuracionActualizada = actualizarConfiguracionUseCase.ejecutar(dto);
        return ResponseEntity.ok(configuracionActualizada);
    }
}
