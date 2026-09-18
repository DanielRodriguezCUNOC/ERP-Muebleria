package com.erp.muebleria.modules.usuarios.application.useCases.usuarios;

import com.erp.muebleria.modules.usuarios.application.dto.CambiarEstadoDTO;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.UsuarioRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CambiarEstadoUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepository;

    public void ejecutar (Long usaurioId, CambiarEstadoDTO dto) {
        Usuario usuario = usuarioRepository.buscarPorId(usaurioId).orElseThrow(
                () -> new RuntimeException("El usuario con id " + usaurioId + " no existe")
        );

        boolean intentarDesactivar = dto.getActivo() != null && !dto.getActivo();

        if (intentarDesactivar && usuario.getActivo()) {
            boolean esAdministrador = usuario.getRol() != null
                    && "ADMINISTRADOR".equalsIgnoreCase(usuario.getRol().getNombre());

            if (esAdministrador) {
                long adminsActivos = usuarioRepository.contarAdministradoresActivos();
                if (adminsActivos <= 1)
                    throw new RuntimeException("No se puede desactivar el último administrador activo.");
            }
            usuario.desactivar();
        }else if (dto.getActivo() != null && dto.getActivo()) {
            usuario.activar();
        }
        usuarioRepository.guardar(usuario);
    }
}
