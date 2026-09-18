package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.application.dto.ModificarEmpleadoDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Rol;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.RolRepositoryPort;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModificarEmpleadosUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final RolRepositoryPort rolRepository;

    public void ejecutar(Long id, ModificarEmpleadoDTO dto){
        Usuario usuario = usuarioRepository.buscarPorId(id).orElseThrow(
                () -> new RuntimeException("El usuario con id " + id + " no existe")
        );

        Rol nuevoRol = null;
        if (dto.getRolId() != null)
            nuevoRol = rolRepository.buscarPorId(dto.getRolId()).orElseThrow(
                    () -> new RuntimeException("El rol con id " + dto.getRolId() + " no existe")
            );
        usuario.actualizarInformacion(dto.getUsuario(), dto.getNumeroTelefono(), dto.getAreaId(), nuevoRol);

        usuarioRepository.guardarUsuarioModificado(usuario);
    }
}
