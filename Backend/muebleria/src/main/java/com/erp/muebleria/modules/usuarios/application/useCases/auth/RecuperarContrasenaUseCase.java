package com.erp.muebleria.modules.usuarios.application.useCases.auth;

import com.erp.muebleria.modules.usuarios.application.dto.RecuperarContrasenaDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.EmpleadoValidacionPort;
import com.erp.muebleria.modules.usuarios.domain.ports.PasswordHasherPort;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.Getter;
import lombok.Setter;

/**
 * Este caso de uso hace:
 * Busqueda del usuario
 * Generar token temporal de recuperacion
 * Enviar un correo
 */
@Getter
@Setter
public class RecuperarContrasenaUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final EmpleadoValidacionPort empleadoValidacionPort;
    private final PasswordHasherPort passwordHasher;

    public RecuperarContrasenaUseCase(UsuarioRepositoryPort usuarioRepository, EmpleadoValidacionPort empleadoValidacionPort, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.empleadoValidacionPort = empleadoValidacionPort;
        this.passwordHasher = passwordHasher;
    }

    public void ejecutar(RecuperarContrasenaDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorUsuario(dto.getUsuario()).
                orElseThrow( () -> new RuntimeException("Credenciales o DPI inválido"));

        boolean dpiValido = empleadoValidacionPort.validarDpiDeUsuario(usuario.getId(), dto.getDpi());

        if (!dpiValido)
            throw new RuntimeException("Credenciale o DPI inválidos");

        String nuevaContrasenaHasheada = passwordHasher.hash(dto.getNewPassword());

        usuario.setPassword(nuevaContrasenaHasheada);
        usuarioRepository.guardar(usuario);
    }
}
