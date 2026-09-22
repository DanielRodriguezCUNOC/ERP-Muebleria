package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.application.dto.CrearUsuarioDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.PasswordHasherPort;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.Getter;
import lombok.Setter;

/**
 * Este caso de uso se compone de:
 * Buscar el rol en la DB
 * Hashear la password
 * Guadar el nuevo suarioa
 */
@Getter
@Setter
public class CrearUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final RolRepositoryPort rolRepository;
    private final PasswordHasherPort  passwordHasher;

    public CrearUsuarioUseCase(UsuarioRepositoryPort usuarioRepository, RolRepositoryPort rolRepository, PasswordHasherPort passwordHasher) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordHasher = passwordHasher;
    }

    public void ejecutar (CrearUsuarioDTO dto) {
        if (usuarioRepository.existePorUsuario(dto.getUsuario())) throw new RuntimeException("El nombre de usuario ya existe");

        Rol rol = rolRepository.buscarPorId(dto.getRolId()).
                orElseThrow(() -> new RuntimeException("El rol no existe"));

        String hashedPassword = passwordHasher.hash(dto.getPassword());

        Usuario nuevoUsuario = new Usuario(
                null,
                dto.getUsuario(),
                hashedPassword,
                rol
        );

        usuarioRepository.guardar(nuevoUsuario);
    }
}
