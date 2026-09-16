package com.erp.muebleria.modules.administracion.infrastructure.controllers;

import com.erp.muebleria.modules.administracion.application.dto.ActualizarConfiguracionDTO;
import com.erp.muebleria.modules.administracion.application.useCases.ActualizarConfiguracionUseCase;
import com.erp.muebleria.modules.administracion.domain.entities.ConfiguracionSistema;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/admin/configuracion")
@AllArgsConstructor
public class ConfiguracionController {

    private final ActualizarConfiguracionUseCase actualizarConfiguracionUseCase;

    @PutMapping
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<ConfiguracionSistema> actualizarConfiguracion(@RequestBody ActualizarConfiguracionDTO dto) {
        ConfiguracionSistema configuracionActualizada = actualizarConfiguracionUseCase.ejecutar(dto);
        return ResponseEntity.ok(configuracionActualizada);
    }
}
