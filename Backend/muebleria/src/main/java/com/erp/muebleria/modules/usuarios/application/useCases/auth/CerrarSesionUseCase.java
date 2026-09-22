package com.erp.muebleria.modules.usuarios.application.useCases.auth;

import com.erp.muebleria.modules.usuarios.domain.ports.TokenServicePort;

/**
 * Invalida el JWT para que no pueda ser usado
 */
public class CerrarSesionUseCase {

    private final TokenServicePort tokenService;

    public CerrarSesionUseCase(TokenServicePort tokenService) {
        this.tokenService = tokenService;
    }

    public void ejecutar (String token) {
        if (token == null || token.trim().isEmpty())
            throw new IllegalArgumentException("El token no puede estar vacío");

        tokenService.invalidarToken (token);
    }
}
