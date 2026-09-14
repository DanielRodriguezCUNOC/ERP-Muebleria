package com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByUsuario(String usuario);
    boolean existsByUsuario (String usuario);
}
