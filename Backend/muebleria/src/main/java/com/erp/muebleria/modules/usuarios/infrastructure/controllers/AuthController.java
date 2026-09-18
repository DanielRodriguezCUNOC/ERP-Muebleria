package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.LoginDTO;
import com.erp.muebleria.modules.usuarios.application.dto.RecuperarContrasenaDTO;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.CerrarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.IniciarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.RecuperarContrasenaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints públicos para login, logout y recuperar contraseña.")
public class AuthController {

    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;
    private final RecuperarContrasenaUseCase recuperarContrasenaUseCase;

    public AuthController(IniciarSesionUseCase iniciarSesionUseCase, CerrarSesionUseCase cerrarSesionUseCase, RecuperarContrasenaUseCase recuperarContrasenaUseCase) {
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
        this.recuperarContrasenaUseCase = recuperarContrasenaUseCase;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesión", description = "Genera un JWT para acceder a los modulos.")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        String TOKEN = iniciarSesionUseCase.ejecutar(dto);
        return ResponseEntity.ok(TOKEN);
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar Sesión", description = "Invalida el JWT.")
    public ResponseEntity<Void> logout (@RequestHeader("Authorization") String authHeader){
        String token = authHeader.replace("Bearer ", "");
        cerrarSesionUseCase.ejecutar(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/recuperar-contrasena")
    @Operation(summary = "Recuperar Contraseña", description = "Permite cambiar la contraseña validando el usuario y DPI.")
    public ResponseEntity<Void> recuperarContrasena(@RequestBody RecuperarContrasenaDTO dto){
        recuperarContrasenaUseCase.ejecutar(dto);
        return ResponseEntity.ok().build();
    }


}
