package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.CrearUsuarioDTO;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.AsignarRolUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.CrearUsuarioUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Gestión de cuentas de usuario y asignación de roles")
@SecurityRequirement(name = "BearerAuth")
public class UsuarioController {
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final AsignarRolUseCase asignarRolUseCase;

    public UsuarioController(CrearUsuarioUseCase crearUsuarioUseCase, AsignarRolUseCase asignarRolUseCase) {
        this.crearUsuarioUseCase = crearUsuarioUseCase;
        this.asignarRolUseCase = asignarRolUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> crearUsuario(@RequestBody CrearUsuarioDTO dto){
        crearUsuarioUseCase.ejecutar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{usuarioId}/rol/{rolId}")
    public ResponseEntity<Void> asignarRol(@PathVariable Long usuarioId, @PathVariable Long rolId){
        asignarRolUseCase.ejecutar(usuarioId, rolId);
        return ResponseEntity.ok().build();
    }
}
