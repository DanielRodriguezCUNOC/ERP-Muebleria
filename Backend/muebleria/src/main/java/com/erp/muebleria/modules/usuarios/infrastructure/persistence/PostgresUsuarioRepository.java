package com.erp.muebleria.modules.usuarios.infrastructure.persistence;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers.UsuarioMapper;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Adaptador que implementa un puerto que:
 * Traduce entidades de la capa de dominio a entidades para JPA
 */
@Repository
@AllArgsConstructor
public class PostgresUsuarioRepository implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository springDataUsuarioRepository;

    @Override
    public void guardar(Usuario usuario) {
        //* Convertir el POJO a JPA
        UsuarioJpaEntity entity = UsuarioMapper.toEntity(usuario);

        springDataUsuarioRepository.save(entity);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        /**
         * Buscar el DB segun el id
         * Mapea a un POJO el resultado obtenido
         */
        return springDataUsuarioRepository.findById(id).map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return springDataUsuarioRepository.findByUsuario(usuario).map(UsuarioMapper::toDomain);
    }

    @Override
    public boolean existePorUsuario(String usuario) {
        return springDataUsuarioRepository.existsByUsuario(usuario);
    }
}
