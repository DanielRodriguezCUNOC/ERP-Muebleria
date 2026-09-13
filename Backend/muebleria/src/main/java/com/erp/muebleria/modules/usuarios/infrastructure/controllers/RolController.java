package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.CrearRolDTO;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.AsignarPermisoUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.CrearRolUseCase;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final CrearRolUseCase crearRolUseCase;
    private final AsignarPermisoUseCase asignarPermisoUseCase;

    public RolController(
            CrearRolUseCase crearRolUseCase,
            AsignarPermisoUseCase asignarPermisoUseCase
    ){
        this.crearRolUseCase = crearRolUseCase;
        this.asignarPermisoUseCase = asignarPermisoUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> crearRol(@RequestBody CrearRolDTO dto){
        crearRolUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{rolId}/permisos/{permisoId}")
    public ResponseEntity<Void> asignarPermiso(@PathVariable Long rolId, @PathVariable Long permisoId){
        asignarPermisoUseCase.ejecutar(rolId, permisoId);
        return ResponseEntity.ok().build();
    }
}
