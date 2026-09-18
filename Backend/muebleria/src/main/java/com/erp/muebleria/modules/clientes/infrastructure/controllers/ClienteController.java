package com.erp.muebleria.modules.clientes.infrastructure.controllers;

import com.erp.muebleria.modules.clientes.application.dto.ClienteResponseDTO;
import com.erp.muebleria.modules.clientes.application.dto.ModificarClienteDTO;
import com.erp.muebleria.modules.clientes.application.useCases.CambiarEstadoClienteUseCase;
import com.erp.muebleria.modules.clientes.application.useCases.ModificarClienteUseCase;
import com.erp.muebleria.modules.clientes.application.useCases.ObtenerClientePorIdUseCase;
import com.erp.muebleria.modules.clientes.application.useCases.ObtenerClientesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@AllArgsConstructor
public class ClienteController {

    private final ModificarClienteUseCase modificarClienteUseCase;
    private final CambiarEstadoClienteUseCase cambiarEstadoClienteUseCase;
    private final ObtenerClientePorIdUseCase obtenerClientePorIdUseCase;
    private final ObtenerClientesUseCase obtenerClientesUseCase;

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRACION', 'VENTAS')")
    public ResponseEntity<Void> modificarCliente(
            @PathVariable Long id,
            @RequestBody ModificarClienteDTO dto) {
        modificarClienteUseCase.ejecutar(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{clienteId}/estado")
    @PreAuthorize("hasAnyAuthority('ADMINISTRACION', 'VENTAS')")
    @Operation(summary = "Activar o Desactivar cliente", description = "Cambia el estado lógico de un cliente.")
    public ResponseEntity<Void> cambiarEstadoCliente(
            @PathVariable Long clienteId,
            @RequestParam Boolean activo) {
        cambiarEstadoClienteUseCase.ejecutar(clienteId, activo);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMINISTRACION', 'VENTAS')")
    @Operation(summary = "Listar clientes", description = "Obtiene el listado completo de clientes registrados.")
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(obtenerClientesUseCase.ejecutar());
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRACION', 'VENTAS')")
    @Operation(summary = "Obtener cliente por ID", description = "Consulta los detalles de un cliente por su identificador.")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long clienteId) {
        return ResponseEntity.ok(obtenerClientePorIdUseCase.ejecutar(clienteId));
    }
}