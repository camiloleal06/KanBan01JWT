package org.kanban.controller;

import java.util.HashSet;
import java.util.Set;

import org.kanban.dto.CreaUsuarioDTO;
import org.kanban.dto.LoginDTO;
import org.kanban.dto.Msg;
import org.kanban.entities.Rol;
import org.kanban.entities.Usuario;
import org.kanban.seguridad.dto.JwtDTO;
import org.kanban.seguridad.jwt.JwtProvider;
import org.kanban.seguridad.modelo.RolEnum;
import org.kanban.service.RolService;
import org.kanban.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/kanban/api/autenticar")
@CrossOrigin
public class UsuarioAuthController {
	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtProvider jwtProvider;

	
	@ApiOperation(notes = "Registra un nuevo Usuario", value = "Registrar Usuario", tags = { "Usuarios" })
	@PostMapping("/registrar")
	public ResponseEntity<?> nuevo(@RequestBody CreaUsuarioDTO nuevoUsuario) {
		if (usuarioService.existeByUsername(nuevoUsuario.getUsername()))
			return new ResponseEntity<>("ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		Usuario usuario = new Usuario(
				nuevoUsuario.getIdentificacion(), 
				nuevoUsuario.getNombrecompleto(),
				nuevoUsuario.getUsername(), 
				nuevoUsuario.getTelefono(),
				passwordEncoder.encode(nuevoUsuario.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.findByRol(RolEnum.ROLE_USER).get());
		if (nuevoUsuario.getRoles().contains("admin"))
			roles.add(rolService.findByRol(RolEnum.ROLE_ADMIN).get());
		usuario.setRoles(roles);
		
		usuarioService.saveUsuario(usuario);
		return new ResponseEntity<>(new Msg("Usuario Guardado"), HttpStatus.CREATED);
	}

	@ApiOperation(notes = "Login y obtenemos el Token", value = "Login", tags = { "Usuarios" })
	@PostMapping("/login")
	public ResponseEntity<JwtDTO> Login(@RequestBody LoginDTO loginDTO) {
		 Authentication auth = authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtProvider.crearToken(auth);
		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());
		return new ResponseEntity<JwtDTO>(jwtDTO, HttpStatus.OK);
    	}
    }
