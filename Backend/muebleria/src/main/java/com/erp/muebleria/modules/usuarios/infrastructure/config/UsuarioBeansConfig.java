package com.erp.muebleria.modules.usuarios.infrastructure.config;

import com.erp.muebleria.modules.clientes.domain.ports.ClienteRepositoryPort;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.CerrarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.IniciarSesionUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.auth.RecuperarContrasenaUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.AsignarPermisoUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.rolesPermisos.CrearRolUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.AsignarRolUseCase;
import com.erp.muebleria.modules.usuarios.application.useCases.usuarios.CrearUsuarioUseCase;
import com.erp.muebleria.modules.clientes.application.useCases.ModificarClienteUseCase;
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
public class UsuarioBeansConfig {

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(UsuarioRepositoryPort uRepo, RolRepositoryPort rRepo, PasswordHasherPort hasher) {
        return new CrearUsuarioUseCase(uRepo, rRepo, hasher);
    }

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(UsuarioRepositoryPort uRepo, PasswordHasherPort hasher, TokenServicePort tokenService) {
        return new IniciarSesionUseCase(uRepo, hasher, tokenService);
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(TokenServicePort tokenService) {
        return new CerrarSesionUseCase(tokenService);
    }

    @Bean
    public RecuperarContrasenaUseCase recuperarContrasenaUseCase(UsuarioRepositoryPort uRepo, EmpleadoValidacionPort valPort, PasswordHasherPort hasher) {
        return new RecuperarContrasenaUseCase(uRepo, valPort, hasher);
    }

    @Bean
    public CrearRolUseCase crearRolUseCase(RolRepositoryPort rRepo) {
        return new CrearRolUseCase(rRepo);
    }

    @Bean
    public AsignarPermisoUseCase asignarPermisoUseCase(RolRepositoryPort rRepo) {
        return new AsignarPermisoUseCase(rRepo);
    }

    @Bean
    public AsignarRolUseCase asignarRolUseCase(UsuarioRepositoryPort uRepo, RolRepositoryPort rRepo) {
        return new AsignarRolUseCase(uRepo, rRepo);
    }

    @Bean
    public ModificarEmpleadosUseCase modificarEmpleadosUseCase(UsuarioRepositoryPort uRepo, RolRepositoryPort rRepo) {
        return new ModificarEmpleadosUseCase(uRepo, rRepo);
    }


}
