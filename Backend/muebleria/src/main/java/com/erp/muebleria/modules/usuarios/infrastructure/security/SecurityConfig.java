package com.erp.muebleria.modules.usuarios.infrastructure.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter  jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).
                sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )).
                authorizeHttpRequests(auth -> auth.
                        requestMatchers("/api/auth/**").permitAll().
                        requestMatchers("api/compras/**").hasAnyRole("ADMINISTRADOR", "COMPRAS").
                        requestMatchers("/api/ventas/**").hasAnyRole("ADMINISTRADOR", "VENTAS").
                        requestMatchers("/api/roles/**", "api/usuarios/**").hasRole("ADMINISTRADOR").
                        anyRequest().authenticated()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
