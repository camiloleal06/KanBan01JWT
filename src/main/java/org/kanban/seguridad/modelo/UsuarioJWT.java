package org.kanban.seguridad.modelo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.kanban.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioJWT implements UserDetails  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String identificacion;
	private String nombrecompleto;
	@Column(unique = true)
	private String username;
	private String telefono;
	private String password;
	private Collection<? extends GrantedAuthority> autorities;
	
	public UsuarioJWT(String identificacion, String nombrecompleto, String username, String telefono,
			String password, Collection<? extends GrantedAuthority> autorities) {
		this.identificacion = identificacion;
		this.nombrecompleto = nombrecompleto;
		this.username = username;
		this.telefono = telefono;
		this.password = password;
		this.autorities = autorities;
	}
	
	
	public UsuarioJWT() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return autorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	public String getNombrecompleto() {
		return nombrecompleto;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getIdentificacion() {
		return identificacion;
	}


	
	public static UsuarioJWT convertir(Usuario usuario) {
	          List<GrantedAuthority> authorities =
	                usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol
	                .getRol().name())).collect(Collectors.toList());
	          
	             return new UsuarioJWT(usuario.getIdentificacion(), 
	        		usuario.getNombrecompleto(),usuario.getUsername(),
	        		usuario.getTelefono(),usuario.getPassword(), authorities);
	    		
	}
	
	
	
}
