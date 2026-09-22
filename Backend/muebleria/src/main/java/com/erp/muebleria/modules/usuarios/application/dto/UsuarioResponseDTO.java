package com.erp.muebleria.modules.usuarios.application.dto;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String usuario;
    private String dpi;
    private String numeroTelefono;
    private Boolean activo;
    private Long areaId;
    private Long rolId;
    private String nombreRol;

    public static UsuarioResponseDTO desdeDominio(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getUsuario(),
                usuario.getDpi(),
                usuario.getNumeroTelefono(),
                usuario.getActivo(),
                usuario.getAreaId(),
                usuario.getRol() != null ? usuario.getRol().getId() : null,
                usuario.getRol() != null ? usuario.getRol().getNombre() : null
        );
    }
}