package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.CrearRolDTO;
import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.application.dto.ModificarRolDTO;
import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.*;
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
@RequestMapping("/api/v1/admin/roles")
@Tag(name = "Rol", description = "Gestión de roles y permisos relacionados a estos roles")
@SecurityRequirement(name = "BearerAuth")
@AllArgsConstructor
public class RolController {

    private final CrearRolUseCase crearRolUseCase;
    private final AsignarPermisoUseCase asignarPermisoUseCase;
    private final ModificarRolUseCase modificarRolUseCase;
    private final EliminarRolUseCase eliminarRolUseCase;
    private final QuitarPermisoUseCase quitarPermisoUseCase;
    private final ObtenerRolesUseCase obtenerRolesUseCase;
    private final ObtenerPermisosUseCase obtenerPermisosUseCase;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    @Operation(summary = "Crear rol", description = "Permite registrar un nuevo rol en el sistema.")
    public ResponseEntity<Void> crearRol(@RequestBody CrearRolDTO dto) {
        crearRolUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{rolId}/permisos/{permisoId}")
    @PreAuthorize("hasAuthority('PERMISOS_GESTIONAR')")
    @Operation(summary = "Asignar permiso a un rol", description = "Permite asignar un permiso específico a un rol existente.")
    public ResponseEntity<Void> asignarPermiso(@PathVariable Long rolId, @PathVariable Long permisoId) {
        asignarPermisoUseCase.ejecutar(rolId, permisoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{rolId}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    @Operation(summary = "Modificar rol", description = "Actualiza el nombre o la descripción de un rol existente.")
    public ResponseEntity<Void> modificarRol(
            @PathVariable Long rolId,
            @RequestBody ModificarRolDTO dto) {
        modificarRolUseCase.ejecutar(rolId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{rolId}")
    @PreAuthorize("hasAuthority('ROLES_GESTIONAR')")
    @Operation(summary = "Desactivar rol", description = "Realiza la baja lógica de un rol si no tiene empleados activos asociados.")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long rolId) {
        eliminarRolUseCase.ejecutar(rolId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{rolId}/permisos/{permisoId}")
    @PreAuthorize("hasAuthority('PERMISOS_GESTIONAR')")
    @Operation(summary = "Quitar permiso a un rol", description = "Elimina un permiso asignado a un rol específico.")
    public ResponseEntity<Void> quitarPermiso(@PathVariable Long rolId, @PathVariable Long permisoId) {
        quitarPermisoUseCase.ejecutar(rolId, permisoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(summary = "Obtener todos los roles", description = "Lista todos los roles registrados en el sistema.")
    public ResponseEntity<List<RolResponseDTO>> obtenerTodosLosRoles() {
        return ResponseEntity.ok(obtenerRolesUseCase.ejecutar());
    }

    @GetMapping("/permisos")
    @PreAuthorize("hasAuthority('USUARIOS_VER')")
    @Operation(summary = "Obtener todos los permisos", description = "Lista todos los permisos registrados en el sistema.")
    public ResponseEntity<List<PermisoResponseDTO>> obtenerTodosLosPermisos() {
        return ResponseEntity.ok(obtenerPermisosUseCase.ejecutar());
    }
}
