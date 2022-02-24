package org.kanban.controller;

import java.util.List;

import org.kanban.dto.CreaTareaDTO;
import org.kanban.dto.Msg;
import org.kanban.entities.Tarea;
import org.kanban.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/kanban/api/")

public class TareaController {

	
	@Autowired
	TareaService tareaService;

	private final String TAREA = "/tarea";
	private final String ID = "/{id}";
	
	@ApiOperation(notes = "Obtenemos todas las tareas, permitido solo para Rol ADMIN", 
			value = "Listar Tareas", tags = { "Tareas" })
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(TAREA)
	public ResponseEntity<List<Tarea>> listTarea () {
		return new ResponseEntity<>(tareaService.findAll(), HttpStatus.OK);
	}
	
	@ApiOperation(notes = "Obtenemos una Tarea por su ID, permitido solo para Rol ADMIN", 
			value = "Tarea pr ID", tags = { "Tareas" })
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(TAREA+ID)
  	public ResponseEntity<?> listTareaById (@PathVariable int id) {
  		return new ResponseEntity<>(tareaService.findById(id).orElseThrow(), HttpStatus.OK);
  	}
  	
	@ApiOperation(notes = "Obtenemos una tarea para eliminar, permitido solo para Rol ADMIN", 
			value = "Eliminar Tarea", tags = { "Tareas" })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(TAREA + ID)
	public ResponseEntity<?> eliminarTareaById(@PathVariable("id") int id) {
    	if (!tareaService.existsById(id))
			return new ResponseEntity<>(new Msg("La tarea con ID " + id + " no existe"), HttpStatus.NOT_FOUND);
	        	tareaService.delete(id);
		    return new ResponseEntity<>(new Msg("Tarea eliminada"), HttpStatus.OK);
	}
	
	
	@ApiOperation(notes = "Creamos una Tarea asignandole un Usuario responsable, permitido solo para Rol ADMIN", 
			value = "Crear Tarea", tags = { "Tareas" })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(TAREA)
	public ResponseEntity<?> nuevaTarea(@RequestBody CreaTareaDTO nuevaTarea) {
          tareaService.save(nuevaTarea);
 		return new ResponseEntity<>(new Msg("Tarea Guardada"), HttpStatus.CREATED);
	}
}
