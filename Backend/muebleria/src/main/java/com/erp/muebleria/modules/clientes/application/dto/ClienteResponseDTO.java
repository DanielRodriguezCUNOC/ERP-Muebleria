package com.erp.muebleria.modules.clientes.application.dto;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private Boolean activo;
    private String mensaje;

    public ClienteResponseDTO(Long id, String nombre, String nit, String direccion, String telefono, Boolean activo) {
    }

    public static ClienteResponseDTO desdeDominio(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getNit(),
                cliente.getDireccion(),
                cliente.getTelefono(),
                cliente.getActivo()
        );
    }
}
