package com.erp.muebleria.modules.usuarios.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/compras/**").hasAnyAuthority("COMPRAS_VER", "COMPRAS_GETIONAR")
                        .requestMatchers("/api/v1/ventas/**").hasAnyAuthority("VENTAS_VER", "VENTAS_GESTIONAR")
                        .requestMatchers("/api/v1/inventario/**").hasAnyAuthority("INVENTARIO_VER", "INVENTARIO_GESTIONAR")
                        .requestMatchers("/api/v1/admin/roles/**").hasAnyAuthority("ROLES_GESTIONAR", "PERMISOS_GESTIONAR")
                        .requestMatchers("/api/v1/usuarios/**").hasAnyAuthority("USUARIOS_VER", "USUARIOS_GESTIONAR")
                        .requestMatchers("/api/v1/reportes/**").hasAnyAuthority("REPORTES_VER", "INVENTARIO_GESTIONAR", "COMPRAS_GESTIONAR", "VENTAS_GESTIONAR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}