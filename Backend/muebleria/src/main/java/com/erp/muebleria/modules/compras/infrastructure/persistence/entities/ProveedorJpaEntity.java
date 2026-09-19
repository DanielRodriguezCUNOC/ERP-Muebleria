package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "telefono_contacto", length = 30)
    private String telefonoContacto;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;
}
