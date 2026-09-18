package com.erp.muebleria.modules.usuarios.infrastructure.persistence;

import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers.UsuarioMapper;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataPermisoRepository;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa un puerto que:
 * Traduce entidades de la capa de dominio a entidades para JPA
 */
@Repository
@AllArgsConstructor
public class PostgresUsuarioRepository implements UsuarioRepositoryPort {

    private final SpringDataUsuarioRepository springDataUsuarioRepository;
    private final SpringDataPermisoRepository springDataPermisoRepository;

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

    @Override
    public Usuario guardarUsuarioModificado(Usuario usuario) {
        UsuarioJpaEntity entity = UsuarioMapper.toEntity(usuario);
        UsuarioJpaEntity savedEntity = springDataUsuarioRepository.save(entity);
        return UsuarioMapper.toDomain(savedEntity);
    }

    @Override
    public long contarAdministradoresActivos() {
        return springDataUsuarioRepository.contarAdministradoresActivos();
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return springDataUsuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDomain)
                .toList();
    }

    @Override
    public List<PermisoResponseDTO> obtenerPermisosPorUsuarioId(Long usuarioId) {
        return springDataPermisoRepository.obtenerPermisosPorUsuarioId(usuarioId);
    }

    @Override
    public boolean existeUsuario(Long usuarioId) {
        return springDataUsuarioRepository.existsById(usuarioId);
    }
}
