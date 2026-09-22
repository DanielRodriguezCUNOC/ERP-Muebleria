package com.erp.muebleria.modules.compras.application.useCases;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.muebleria.modules.compras.application.dto.ProveedorResponseDTO;
import com.erp.muebleria.modules.compras.domain.ports.ProveedorRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultarProveedoresUseCase {

    private final ProveedorRepositoryPort proveedorRepositoryPort;

    @Transactional(readOnly = true)
    public List<ProveedorResponseDTO> ejecutar(String nombre) {
        return proveedorRepositoryPort.buscarTodos(nombre);
    }
}
