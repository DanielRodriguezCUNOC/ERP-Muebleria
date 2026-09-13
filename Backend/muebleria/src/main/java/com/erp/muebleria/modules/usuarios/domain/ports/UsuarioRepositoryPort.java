package com.erp.muebleria.modules.usuarios.domain.ports;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;

import java.util.Optional;

/**
 * Esta interfaz define esto:
 * Persistencia y recuperacion de datos del usaurio
 * El Optional se usa para el manejo de nulos
 */

public interface UsuarioRepositoryPort {

    void guardar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUsuario(String usuario);
    boolean existePorUsuario(String usuario);
}
