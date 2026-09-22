package com.erp.muebleria.modules.usuarios.infrastructure.controllers;

import com.erp.muebleria.modules.usuarios.application.dto.AuthResponseDTO;
import com.erp.muebleria.modules.usuarios.application.dto.LoginDTO;
import com.erp.muebleria.modules.usuarios.application.dto.RecuperarContrasenaDTO;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.CerrarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.IniciarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.RecuperarContrasenaUseCase;
import com.erp.muebleria.modules.usuarios.infrastructure.security.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Endpoints públicos para login, logout y recuperar contraseña.")
public class AuthController {

    private final IniciarSesionUseCase iniciarSesionUseCase;
    private final CerrarSesionUseCase cerrarSesionUseCase;
    private final RecuperarContrasenaUseCase recuperarContrasenaUseCase;
    private final JwtTokenService jwtTokenService;

    public AuthController(IniciarSesionUseCase iniciarSesionUseCase, CerrarSesionUseCase cerrarSesionUseCase, RecuperarContrasenaUseCase recuperarContrasenaUseCase, JwtTokenService jwtTokenService) {
        this.iniciarSesionUseCase = iniciarSesionUseCase;
        this.cerrarSesionUseCase = cerrarSesionUseCase;
        this.recuperarContrasenaUseCase = recuperarContrasenaUseCase;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar Sesión", description = "Genera un JWT para acceder a los modulos.")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO dto){
        String token = iniciarSesionUseCase.ejecutar(dto);
        Instant expiresAt = jwtTokenService.obtenerExpiracionDelToken(token);
        return ResponseEntity.ok(new AuthResponseDTO(token, expiresAt));
    }

    @PostMapping("/logout")
    @Operation(summary = "Cerrar Sesión", description = "Invalida el JWT.")
    public ResponseEntity<Void> logout (@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || authHeader.isBlank()) {
            throw new IllegalArgumentException("Authorization header es requerido");
        }
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
