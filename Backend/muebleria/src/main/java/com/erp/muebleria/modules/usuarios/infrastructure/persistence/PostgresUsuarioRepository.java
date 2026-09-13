package com.erp.muebleria.modules.usuarios.infrastructure.persistence;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador que implementa un puerto que:
 * Traduce entidades de la capa de dominio a entidades para JPA
 */
@Repository
public class PostgresUsuarioRepository implements UsuarioRepositoryPort {



    @Override
    public void guardar(Usuario usuario) {

    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return Optional.empty();
    }

    @Override
    public boolean existePorUsuario(String usuario) {
        return false;
    }
}
