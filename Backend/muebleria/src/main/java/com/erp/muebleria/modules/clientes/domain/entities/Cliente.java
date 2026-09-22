package com.erp.muebleria.modules.clientes.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private Long id;
    private String nombre;
    private String nit;
    private String direccion;
    private String telefono;
    private Boolean activo;

    public void actualizarInformacion(String nombre, String nit, String direccion, String telefono) {
        if (nombre != null) this.nombre = nombre;
        if (nit != null) this.nit = nit;
        if (direccion != null) this.direccion = direccion;
        if (telefono != null) this.telefono = telefono;
    }

    public void desactivar() { this.activo = false; }
    public void activar() { this.activo = true; }
}
