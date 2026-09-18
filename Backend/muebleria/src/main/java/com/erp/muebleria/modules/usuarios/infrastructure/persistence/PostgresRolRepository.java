package com.erp.muebleria.modules.usuarios.infrastructure.persistence;

import com.erp.muebleria.modules.usuarios.application.dto.RolResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.RolJpaEntity;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers.PermisoMapper;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.mappers.RolMapper;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataPermisoRepository;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataRolRepository;
import com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories.SpringDataUsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PostgresRolRepository implements RolRepositoryPort {

    private final SpringDataRolRepository springDataRolRepository;
    private final SpringDataPermisoRepository springDataPermisoRepository;
    private final SpringDataUsuarioRepository springDataUsuarioRepository;

    @Override
    public void guardar(Rol rol) {
        RolJpaEntity entity = RolMapper.toEntity(rol);
        springDataRolRepository.save(entity);
    }

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return springDataRolRepository.findById(id)
                .map(RolMapper::toDomain);
    }

    @Override
    public Optional<Permiso> buscarPermisoPorId(Long id) {
        return springDataPermisoRepository.findById(id)
                .map(PermisoMapper::permisoToDomain);
    }

    @Override
    public List<Permiso> buscarPermisosPorIds(List<Long> ids) {
        return springDataPermisoRepository.findAllById(ids)
                .stream()
                .map(PermisoMapper::permisoToDomain)
                .toList();
    }

    @Override
    public boolean tieneUsuariosAsociados(Long rolId) {
        return springDataUsuarioRepository.existePorRolIdYEstaActivo(rolId);
    }

    @Override
    public void quitarPermiso(Long rolId, Long permisoId) {
        springDataRolRepository.eliminarPermisoDeRol(rolId, permisoId);
    }

    @Override
    public boolean existeRol(Long rolId) {
        return springDataRolRepository.existsById(rolId);
    }

    @Override
    public boolean existePermiso(Long permisoId) {
        return springDataPermisoRepository.existsById(permisoId);
    }

    @Override
    public List<RolResponseDTO> obtenerTodosLosRoles() {
        return springDataRolRepository.findAll().stream()
                .map(RolMapper::toResponseDTO)
                .toList();
    }
}