package com.erp.muebleria.modules.administracion.infrastructure.controllers;

import com.erp.muebleria.modules.administracion.application.useCases.GenerarReporteTopClientesUseCase;
import com.erp.muebleria.modules.administracion.domain.models.TopCliente;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reportes")
@AllArgsConstructor
public class ReportesController {

    private final GenerarReporteTopClientesUseCase topClientesUseCase;

    @GetMapping("/top-clientes")
    @PreAuthorize("hasAuthority('ADMINISTRACION')")
    public ResponseEntity<List<TopCliente>> obtenerTopClientes(){
        List<TopCliente> reporte = topClientesUseCase.ejecutar();
        return ResponseEntity.ok(reporte);
    }
}
