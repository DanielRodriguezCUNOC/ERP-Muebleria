package com.erp.muebleria.modules.clientes.application.dto;

import com.erp.muebleria.modules.clientes.domain.entities.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClienteResponseDTO {

    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private Boolean activo;

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
