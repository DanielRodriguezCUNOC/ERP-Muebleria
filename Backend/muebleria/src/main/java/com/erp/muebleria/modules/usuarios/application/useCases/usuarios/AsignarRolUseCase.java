package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;

/**
 * Actualiza las credenciales
 * Con esto se cambian los accesos del iuaurio al sistema
 */
public class AsignarRolUseCase {

    private final UsuarioRepositoryPort  usuarioRepository;
    private final RolRepositoryPort rolRepository;

    public AsignarRolUseCase(UsuarioRepositoryPort usuarioRepository, RolRepositoryPort rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public void ejecutar (Long usuarioId, Long nuevoRolId) {
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId).
                orElseThrow ( () -> new RuntimeException("Usuario no encontrado"));

        Rol nuevoRol = rolRepository.buscarPorId(nuevoRolId).
                orElseThrow( () -> new RuntimeException("El rol especificado no existe"));

        usuario.setRol(nuevoRol);

        usuarioRepository.guardar(usuario);
    }
}
