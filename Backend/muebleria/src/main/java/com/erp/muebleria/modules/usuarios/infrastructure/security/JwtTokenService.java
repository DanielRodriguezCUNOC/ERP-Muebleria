package com.erp.muebleria.modules.usuarios.infrastructure.security;

import com.erp.muebleria.modules.usuarios.domain.entities.Permiso;
import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtTokenService implements TokenServicePort {

    // * Llave secreta para firmar los tokens XD (debo quitarlo de aqui despues de
    // hacer las pruebas)
    private final Key secretKey;
    private final long expirationTime;
    // * La lista que se llenara para ir manejando cierres de sesion
    private final Set<String> tokenCierreSesion = new HashSet<>();

    public JwtTokenService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expirationTime) {
        // * Convertir el string de configuracion a un arreglo de bytes
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.expirationTime = expirationTime;
    }

    @Override
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expirationTime);

        // * Extraer los codigos de los permisos del rol del usuario
        List<String> permisos = (usuario.getRol() != null && usuario.getRol().getPermisos() != null)
                ? usuario.getRol().getPermisos().stream().map(Permiso::getNombre).toList()
                : List.of();

        return Jwts.builder().setSubject(usuario.getUsuario())
                .claim("rol", usuario.getRol() != null ? usuario.getRol().getNombre() : "SIN_ROL")
                .claim("areaId", usuario.getAreaId())
                .claim("permisos", permisos).setIssuedAt(ahora).setExpiration(expiracion).signWith(secretKey).compact();
    }

    @Override
    public void invalidarToken(String token) {
        tokenCierreSesion.add(token);
    }

    @Override
    public boolean validarToken(String token) {
        try {
            if (tokenCierreSesion.contains(token))
                return false;
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String obtenerUsuarioDelToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> obtenerPermisosDelToken(String token) {
        Claims claims = obtenerClaims(token);
        return claims.get("permisos", List.class);
    }

    public Instant obtenerExpiracionDelToken(String token) {
        Claims claims = obtenerClaims(token);
        Date expiration = claims.getExpiration();
        return expiration != null ? expiration.toInstant() : Instant.now();
    }

    private Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
