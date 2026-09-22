package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.*;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.ObtenerPermisosPorUsuarioUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestión de cuentas de usuario y asignación de roles")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final ModificarEmpleadosUseCase modificarEmpleadosUseCase;
    private final AsignarRolUseCase asignarRolUseCase;
    private final CambiarEstadoUsuarioUseCase cambiarEstadoUsuarioUseCase;
    private final ObtenerUsuarioUseCase obtenerUsuarioUseCase;
    private final ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase;
    private final ObtenerPermisosPorUsuarioUseCase obtenerPermisosPorUsuarioUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    @Operation(summary = "Crear usuario", description = "Permite registrar un nuevo usuario en el sistema.")
    public ResponseEntity<Void> crearUsuario(@RequestBody CrearUsuarioDTO dto){
        crearUsuarioUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{usuarioId}/rol/{rolId}")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    @Operation(summary = "Asignar rol", description = "Permite asignar o cambiar el rol de un usuario.")
    public ResponseEntity<Void> asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId){
        asignarRolUseCase.ejecutar(usuarioId, rolId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    @Operation(summary = "Modificar empleado", description = "Permite modificar la información de un empleado.")
    public ResponseEntity<Void> modificarEmpleado(@PathVariable Long id, @RequestBody ModificarEmpleadoDTO dto) {
        modificarEmpleadosUseCase.ejecutar(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{usuarioId}/estado")
    @PreAuthorize("hasAuthority('USUARIOS_GESTIONAR')")
    @Operation(summary = "Cambiar estado de empleado", description = "Permite activar o desactivar un empleado.")
    public ResponseEntity<Void> cambiarEstadoEmpleado(@PathVariable Long usuarioId, @RequestBody CambiarEstadoDTO dto) {
        cambiarEstadoUsuarioUseCase.ejecutar(usuarioId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(summary = "Obtener todos los empleados", description = "Permite obtener una lista de todos los empleados.")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosLosEmpleados() {
        return ResponseEntity.ok(obtenerUsuarioUseCase.ejecutar());
    }

    @GetMapping("/{usuarioId}")
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(summary = "Obtener empleado por ID", description = "Permite obtener la información de un empleado por su ID.")
    public ResponseEntity<UsuarioResponseDTO> obtenerEmpleadoPorId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(obtenerUsuarioPorIdUseCase.ejecutar(usuarioId));
    }

    @GetMapping("/{usuarioId}/permisos")
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(summary = "Obtener permisos de un usuario", description = "Obtiene la lista de permisos asignados a un usuario a través de su rol.")
    public ResponseEntity<List<PermisoResponseDTO>> obtenerPermisosPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(obtenerPermisosPorUsuarioUseCase.ejecutar(usuarioId));
    }
}
