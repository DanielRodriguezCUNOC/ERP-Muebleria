package com.erp.muebleria.modules.usuarios.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "empleado")
@Getter
@Setter
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String usuario;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String dpi;

    @Column(name = "numero_telefono")
    private String numeroTelefono;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "area_id")
    private Long areaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id", nullable = false)
    private RolJpaEntity rol;
}