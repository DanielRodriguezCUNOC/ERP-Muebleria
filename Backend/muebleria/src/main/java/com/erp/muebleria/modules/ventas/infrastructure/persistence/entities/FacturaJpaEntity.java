package com.erp.muebleria.modules.ventas.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sale_id", nullable = false)
    private Long saleId;

    @Column(name = "numero_factura", unique = true, length = 100)
    private String numeroFactura;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();

    @Column(name = "cliente_nit", length = 30)
    private String clienteNit;

    @Column(name = "cliente_nombre", length = 150)
    private String clienteNombre;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
}