package org.kanban.repository;

import org.kanban.dto.TareaUsuarioDTO;
import org.kanban.entities.Tarea;
import org.kanban.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {
  @Query("SELECT new org.kanban.dto.TareaUsuarioDTO (t.id,t.titulo, t.descripcion,u.id,u.nombrecompleto,u.username)"
          + " FROM Tarea t JOIN t.usuario u where u.id=?1")
  public List<TareaUsuarioDTO> listaTareasByUsuario(int id);

  @Query("SELECT new org.kanban.dto.TareaUsuarioDTO (t.id,t.titulo, t.descripcion,u.id,u.nombrecompleto,u.username)"
          + " FROM Tarea t JOIN t.usuario u")
  public List<TareaUsuarioDTO> listaTareasUsuarios();
  
  @Query("SELECT new org.kanban.dto.TareaUsuarioDTO (t.id,t.titulo, t.descripcion,u.id,u.nombrecompleto,u.username)"
          + " FROM Tarea t JOIN t.usuario u where u.username=?1")
  public List<TareaUsuarioDTO> listaTareasUsuarios(String Username);

  List<Tarea> findByUsuario(Usuario usuario);

  Optional<Tarea> findByTitulo(String titulo);
}
