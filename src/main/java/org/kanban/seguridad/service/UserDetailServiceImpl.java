package org.kanban.seguridad.service;

import org.kanban.entities.Usuario;
import org.kanban.seguridad.modelo.UsuarioJWT;
import org.kanban.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
@Autowired
UsuarioService usuarioService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		        Usuario usuario = usuarioService.findByUsername(username).get();
		        return UsuarioJWT.convertir(usuario);
		 }
	}


