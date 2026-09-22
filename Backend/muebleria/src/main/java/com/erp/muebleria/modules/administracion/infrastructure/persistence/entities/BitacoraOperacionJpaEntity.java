package com.erp.muebleria.modules.administracion.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad JPA que representa la bitácora de operaciones del sistema en la base de datos.
 */

@Entity
@Table(name = "bitacora_operacion")
@Getter
@Setter
public class BitacoraOperacionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @Column(name = "accion", nullable = false, length = 100)
    private String accion;

    @Column(name = "modulo", nullable = false, length = 50)
    private String modulo;

    @Column(name = "detalle", columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;


}
