package org.kanban.repository;

import java.util.Optional;

import org.kanban.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
