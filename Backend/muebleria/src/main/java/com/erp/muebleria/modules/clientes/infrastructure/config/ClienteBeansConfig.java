package com.erp.muebleria.modules.clientes.infrastructure.config;

import com.erp.muebleria.modules.clientes.application.useCases.CambiarEstadoClienteUseCase;
import com.erp.muebleria.modules.clientes.application.useCases.ModificarClienteUseCase;
import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.CerrarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.IniciarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.RecuperarContrasenaUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.AsignarPermisoUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.CrearRolUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.AsignarRolUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.CrearUsuarioUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.ModificarEmpleadosUseCase;
import com.erp.muebleria.modules.usuarios.domain.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Eta configuracion es necesaria debido a que estamos usando PoJos (sin decoradores de Spring)
 * en la capa de application (dto) debemos registrarlos aqui, de eta manera permitimos que se inyecten a los
 * controllers de infrastructure
 *
 */
@Configuration
public class ClienteBeansConfig {

    @Bean
    CambiarEstadoClienteUseCase cambiarEstadoClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new CambiarEstadoClienteUseCase(clienteRepository);
    }

    @Bean
    ModificarClienteUseCase modificarClienteUseCase(ClienteRepositoryPort clienteRepository) {
        return new ModificarClienteUseCase(clienteRepository);
    }
}
