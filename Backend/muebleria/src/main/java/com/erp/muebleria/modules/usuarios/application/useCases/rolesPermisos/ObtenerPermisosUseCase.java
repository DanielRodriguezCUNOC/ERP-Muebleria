package com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.erp.muebleria.modules.usuarios.application.dto.PermisoResponseDTO;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ObtenerPermisosUseCase {

  private final UsuarioRepositoryPort usuarioRepository;

  public List<PermisoResponseDTO> ejecutar() {
    return usuarioRepository.obtenerPermisos();
  }

}
