package org.kanban.controller;

import java.util.List;

import org.kanban.dto.EditarUsuarioDTO;
import org.kanban.dto.Msg;
import org.kanban.dto.TareaUsuarioDTO;
import org.kanban.entities.Usuario;
import org.kanban.seguridad.jwt.JwtProvider;
import org.kanban.service.RolService;
import org.kanban.service.TareaService;
import org.kanban.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
@CrossOrigin
@RestController
@RequestMapping("/kanban/api/")

public class UsuarioController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TareaService tareaService;

	@Autowired
	RolService rolService;
	
	
	private final String TAREA = "/tarea";
	private final String USER_ID = "/{user_id}";
	private final String USUARIO = "usuario";
	private final String USERNAME= "/{username}";
	private final String ID = "/{id}";
	
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(notes = "Obtenemos el Listado de todos los Usuarios, permitido solo para Rol ADMIN", value = "Lista de Usuarios", tags = { "Usuarios" })
    @GetMapping(USUARIO)
	public ResponseEntity<List<Usuario>> listUsuario () {
		return new ResponseEntity<>(usuarioService.listUsuarios(), HttpStatus.OK);
	}
	
   @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(notes = "Obtenemos un usuario para modificarle datos, no se modifica contraseña.Permitido solo para Rol ADMIN", value = "Modificar Usuario", tags = { "Usuarios" })
	@PutMapping(USUARIO+ID)
	public ResponseEntity<?> editUsuario (@PathVariable("id") int id, @RequestBody EditarUsuarioDTO usuario) {
	    if (!usuarioService.existeById(id))
	    	return new ResponseEntity<>("No existe el usuario con este ID", HttpStatus.NOT_FOUND);
	       if(usuarioService.existeByUsername(usuario.getUsername()) &&  
	  	          usuarioService.findByUsername(usuario.getUsername()).get().getId()!=id)
	    	return new ResponseEntity<>("Este nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
	         Usuario user= usuarioService.findByUserID(id).get();
	         user.setNombrecompleto(usuario.getNombrecompleto());
	    	 user.setUsername(usuario.getUsername());
	    	 user.setIdentificacion(usuario.getIdentificacion());
	    	 user.setTelefono(usuario.getTelefono());
	         usuarioService.saveUsuario(user);
	      return new ResponseEntity<>(new Msg("Usuario Actualizado"), HttpStatus.OK);
		}
    
   
	@ApiOperation(notes = "Obtenemos el Usuario y sus tareas asignadas,permitido solo para Rol ADMIN y USER", value = "Lista de tareas por usuario", tags = { "Usuarios" })

	@PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
	@GetMapping(USUARIO + TAREA+USERNAME)
	public ResponseEntity<?> findUsuarioTareaByUserName(@PathVariable String username) {
		List<TareaUsuarioDTO> lista = tareaService.listTareasDTO(username);
		if(lista.isEmpty())
			return new ResponseEntity<>("NO HAY TAREAS ASIGNADAS A ESTE USUARIO", HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	
	@ApiOperation(notes = "Obtenemos un usuario para eliminarlo, esto puede casusar excepción de integridad referencial,permitido solo para Rol ADMIN", 
			value = "Eliminar Usuario", tags = { "Usuarios" })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(USUARIO + ID)
	public ResponseEntity<?> eliminarUsuarioById(@PathVariable("id") int id) {
    	if (!usuarioService.existeById(id))
			return new ResponseEntity<>("El usuario con ID " + id + " no existe", HttpStatus.NOT_FOUND);
		usuarioService.delete(id);
		    return new ResponseEntity<>("Usuario eliminado", HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Obtenemos un usuario por su ID. Permitido solo Rol Admin", value = "Usuario por ID", tags = { "Usuarios" })
	@GetMapping(USUARIO + ID)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> UsuarioById(@PathVariable("id") int id) {
    	if (!usuarioService.existeById(id))
			return new ResponseEntity<>("El usuario con ID " + id + " no existe", HttpStatus.NOT_FOUND);
    	return new ResponseEntity<Usuario>(usuarioService.findByUserID(id).get(), HttpStatus.OK);
	}
}