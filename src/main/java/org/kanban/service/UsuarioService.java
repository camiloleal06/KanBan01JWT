package org.kanban.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.kanban.dto.EditarUsuarioDTO;
import org.kanban.entities.Usuario;
import org.kanban.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {
@Autowired
UsuarioRepository usuarioRepository;

public Optional<Usuario> findByUsername(String username){
	return usuarioRepository.findByUsername(username);
}

public Optional<Usuario> findByUserID(int id){
	return usuarioRepository.findById(id);
}

public boolean existeByUsername(String username){
	return usuarioRepository.existsByUsername(username);
}

public boolean existeById(int id){
	return usuarioRepository.existsById(id);
}

public void saveUsuario(Usuario usuario) {
	usuarioRepository.save(usuario);
}

/*public Usuario editUsuario(EditarUsuarioDTO usuario) {
     Usuario user = new Usuario();
  	 user.setNombrecompleto(usuario.getNombrecompleto());
  	 user.setUsername(usuario.getUsername());
  	 user.setIdentificacion(usuario.getIdentificacion());
  	 user.setTelefono(usuario.getTelefono());
     return usuarioRepository.save(user);

}
*/


public List<Usuario> listUsuarios(){
	return usuarioRepository.findAll();
}

public Optional<Usuario> usuarioById(int id){
	return usuarioRepository.findById(id);
}

public void delete(int id) {
	if(this.usuarioRepository.existsById(id))
	  usuarioRepository.deleteById(id);
}

}
