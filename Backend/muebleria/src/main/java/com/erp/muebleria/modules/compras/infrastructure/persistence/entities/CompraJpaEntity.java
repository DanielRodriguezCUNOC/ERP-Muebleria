package com.erp.muebleria.modules.compras.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "compra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompraJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_compra", nullable = false, updatable = false)
    private LocalDateTime fechaCompra;

    @Column(name = "empleado_id", nullable = false)
    private Long empleadoId;

    @ElementCollection
    @CollectionTable(name = "compra_proveedor", joinColumns = @JoinColumn(name = "compra_id"))
    @Column(name = "proveedor_id")
    private Set<Long> proveedorIds = new HashSet<>();

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompraJpaEntity> detalles = new ArrayList<>();
}
