package org.kanban.seguridad.jwt;

import java.util.Date;

import org.kanban.seguridad.modelo.UsuarioJWT;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {
	
    private String secret="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALZuS4sRiVM1ub9CEyHUqBp6EUo08Hn"
    		+ "azjRBgPfSQMKWop7HpNFfdHhjzuZX7vgJk0tOkkHSK0nrUmpjaDQVECAwEAAQ";
    
    public String crearToken(Authentication authentication){
    	Date dt = new Date();
        UsuarioJWT usuarioJWT = (UsuarioJWT) authentication.getPrincipal();
        return Jwts.builder().setSubject(usuarioJWT.getUsername())
                .setIssuedAt(dt)
                .setExpiration(new Date(dt.getTime() + (1000 * 60 * 60 * 48)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    
    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }
    
    public boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
           System.out.print(e.getMessage());
        }catch (UnsupportedJwtException e){
            System.out.print(e.getMessage());
        }catch (ExpiredJwtException e){
            System.out.print(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.print(e.getMessage());
        }
        return false;
    }
}
