package com.erp.muebleria.modules.compras.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Compra {
    private Long id;
    private LocalDateTime fechaCompra;
    private Set<Long> proveedorIds;
    private Long empleadoId;
    private List<DetalleCompra> detalles;
}
