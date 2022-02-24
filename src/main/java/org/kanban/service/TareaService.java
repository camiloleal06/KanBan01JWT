package org.kanban.service;

import java.util.List;
import java.util.Optional;

import org.kanban.dto.CreaTareaDTO;
import org.kanban.dto.TareaUsuarioDTO;
import org.kanban.entities.Tarea;
import org.kanban.entities.Usuario;
import org.kanban.repository.TareaRepository;
import org.kanban.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TareaService{
@Autowired
TareaRepository tareaRepository;
@Autowired
UsuarioService usuarioRepository;

public Optional<Tarea> findByTitulo(String titulo) {
	return tareaRepository.findByTitulo(titulo);
}

public Optional<Tarea> findById(int id) {
	return tareaRepository.findById(id);
}


public List<Tarea> findAll() {
	return tareaRepository.findAll();
}

public Page<Tarea> findAll(Pageable pageable) {
	// TODO Auto-generated method stub
	return null;
}


public Tarea save(CreaTareaDTO tarea) {
	Tarea nuevaTarea = new Tarea();
	nuevaTarea.setTitulo(tarea.getTitulo());
	nuevaTarea.setDescripcion(tarea.getDescripcion());
	nuevaTarea.setUsuario(usuarioRepository.findByUserID(tarea.getUser_id()).orElse(null));
	return tareaRepository.save(nuevaTarea);
}


public Optional<Tarea> findById(Integer id) {
return tareaRepository.findById(id);
}


public void delete(Integer id) {
	tareaRepository.deleteById(id);
	
}

public List<Tarea> tareasUsuarioAutenticado(Usuario usuario) {
	// TODO Auto-generated method stub
	return tareaRepository.findByUsuario(usuario);
}

public List<TareaUsuarioDTO> listTareasDTO(int id){
	return tareaRepository.listaTareasByUsuario(id);
}

public List<TareaUsuarioDTO> listTareasDTO(){ return tareaRepository.listaTareasUsuarios();
}

public List<TareaUsuarioDTO> listTareasDTO(String username){ return tareaRepository.listaTareasUsuarios(username);
}


public boolean existsById(Integer id) {
	return tareaRepository.existsById(id);
}
}