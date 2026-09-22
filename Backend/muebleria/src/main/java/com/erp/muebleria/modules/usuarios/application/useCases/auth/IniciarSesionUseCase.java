package com.erp.muebleria.modules.usuarios.application.useCases.auth;

import com.erp.muebleria.modules.common.domain.exceptions.CredencialesInvalidasException;
import com.erp.muebleria.modules.usuarios.application.dto.LoginDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.PasswordHasherPort;
import com.erp.muebleria.modules.usuarios.domain.ports.TokenServicePort;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.Getter;
import lombok.Setter;

/**
 * Este caso de uso relaciona:
 * Validacion de credenciales
 * Devuelve un JWT
 */
@Getter
@Setter
public class IniciarSesionUseCase {
    private final UsuarioRepositoryPort usuarioRepository;
    private final PasswordHasherPort passwordHasher;
    private final TokenServicePort tokenService;

    public IniciarSesionUseCase(UsuarioRepositoryPort usuarioRepository, PasswordHasherPort passwordHasher,
            TokenServicePort tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordHasher = passwordHasher;
        this.tokenService = tokenService;
    }

    public String ejecutar(LoginDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorUsuario(dto.getUsuario())
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));

        if (!usuario.getActivo())
            throw new CredencialesInvalidasException("Usuario bloqueado o inactivo. Comuniquese con soporte");

        boolean passwordValida = passwordHasher.verificar(dto.getPassword(), usuario.getPassword());
        if (!passwordValida)
            throw new CredencialesInvalidasException("Credenciales inválidas");

        return tokenService.generarToken(usuario);
    }
}
