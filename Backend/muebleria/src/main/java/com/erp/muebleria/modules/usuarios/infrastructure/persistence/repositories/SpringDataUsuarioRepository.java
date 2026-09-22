package com.erp.muebleria.modules.usuarios.infrastructure.persistence.repositories;

import com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface SpringDataUsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);

    // * Esto sirve para verificar que almenos exista un administrador en la base de
    // datos, para que no se pueda eliminar el unico administrador
    @Query("SELECT COUNT(u) FROM UsuarioJpaEntity u WHERE u.rol.rol = 'ADMINISTRADOR' AND u.activo = true")
    long contarAdministradoresActivos();

    @Query("SELECT COUNT(u) > 0 FROM UsuarioJpaEntity u WHERE u.rol.id = :rolId AND u.activo = true")
    boolean existePorRolIdYEstaActivo(Long id);

}
