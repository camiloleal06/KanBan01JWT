package org.kanban.repository;

import java.util.Optional;

import org.kanban.entities.Rol;
import org.kanban.seguridad.modelo.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRol(RolEnum rolEnum);
}