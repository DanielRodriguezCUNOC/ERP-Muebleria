package com.erp.muebleria.modules.usuarios.infrastructure.security;

import com.erp.muebleria.modules.usuarios.domain.entities.Usuario;
import com.erp.muebleria.modules.usuarios.domain.ports.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenService implements TokenServicePort {

    //* Llave secreta para firmar los tokens XD (debo quitarlo de aqui despues de hacer las pruebas)
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRATION_TIME = 864000000;

    //* La lista que se llenara para ir manejando cierres de sesion
    private final Set<String> tokenCierreSesion = new HashSet<>();

    @Override
    public String generarToken(Usuario usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + EXPIRATION_TIME);

        return Jwts.builder().setSubject(usuario.getUsuario()).
                claim("rol", usuario.getRol() != null ? usuario.getRol().getNombre() : "SIN_ROL").
                setIssuedAt(ahora).
                setExpiration(expiracion).
                signWith(SECRET_KEY).
                compact();
    }

    @Override
    public void invalidarToken(String token) {
        tokenCierreSesion.add(token);
    }

    @Override
    public boolean validarToken(String token) {
        try {
            if (tokenCierreSesion.contains(token)) return false;
            Jwts.parser().setSigningKey(SECRET_KEY).
                    build().
                    parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String obtenerUsuarioDelToken(String token) {
        Claims claims = Jwts.parser().
                setSigningKey(SECRET_KEY).
                build().
                parseClaimsJws(token).
                getBody();

        return claims.getSubject();
    }
}
