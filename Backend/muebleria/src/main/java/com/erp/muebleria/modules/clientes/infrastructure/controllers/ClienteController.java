package com.erp.muebleria.modules.clientes.infrastructure.controllers;

import com.erp.muebleria.modules.clientes.application.dto.*;
import com.erp.muebleria.modules.clientes.application.useCases.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes y sus datos")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class ClienteController {

    private final ModificarClienteUseCase modificarClienteUseCase;
    private final CambiarEstadoClienteUseCase cambiarEstadoClienteUseCase;
    private final ObtenerClientePorIdUseCase obtenerClientePorIdUseCase;
    private final ObtenerClientesUseCase obtenerClientesUseCase;
    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final ObtenerClientePorNitUseCase obtenerClientePorNitUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('VENTAS_GESTIONAR')")
    @Operation(summary = "Registrar cliente", description = "Permite registrar un nuevo cliente en el sistema.")
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody RegistrarClienteRequestDTO request) {
        ClienteResponseDTO response = registrarClienteUseCase.ejecutar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('USUARIOS_GESTIONAR', 'VENTAS_GESTIONAR')")
    @Operation(summary = "Modificar cliente", description = "Permite modificar los datos de un cliente existente.")
    public ResponseEntity<ClienteResponseDTO> modificarCliente(
            @PathVariable("clienteId") Long clienteId,
            @RequestBody ModificarClienteRequestDTO request) {
        ClienteResponseDTO response = modificarClienteUseCase.ejecutar(clienteId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{clienteId}/estado")
    @PreAuthorize("hasAnyAuthority('USUARIOS_GESTIONAR', 'VENTAS_GESTIONAR')")
    @Operation(summary = "Activar o Desactivar cliente", description = "Cambia el estado lógico de un cliente.")
    public ResponseEntity<ClienteResponseDTO> cambiarEstado(
            @PathVariable("clienteId") Long clienteId,
            @RequestBody CambiarEstadoClienteRequestDTO request) {
        ClienteResponseDTO response = cambiarEstadoClienteUseCase.ejecutar(clienteId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USUARIOS_VER', 'VENTAS_VER')")
    @Operation(summary = "Listar clientes", description = "Obtiene el listado paginado de clientes registrados.")
    public ResponseEntity<Page<ClienteResponseDTO>> obtenerTodos(Pageable pageable) {
        return ResponseEntity.ok(obtenerClientesUseCase.ejecutar(pageable));
    }

    @GetMapping("/{clienteId}")
    @PreAuthorize("hasAnyAuthority('USUARIOS_VER', 'VENTAS_VER')")
    @Operation(summary = "Obtener cliente por ID", description = "Consulta los detalles de un cliente por su identificador.")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable("clienteId") Long clienteId) {
        return ResponseEntity.ok(obtenerClientePorIdUseCase.ejecutar(clienteId));
    }

    @GetMapping("/nit/{nit}")
    @PreAuthorize("hasAnyAuthority('USUARIOS_VER', 'VENTAS_VER')")
    @Operation(summary = "Obtener cliente por NIT", description = "Consulta los detalles de un cliente por su número de NIT.")
    public ResponseEntity<ClienteResponseDTO> obtenerPorNit(@PathVariable("nit") String nit) {
        return ResponseEntity.ok(obtenerClientePorNitUseCase.ejecutar(nit));
    }
}