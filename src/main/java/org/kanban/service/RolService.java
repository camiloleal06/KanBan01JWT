package org.kanban.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.kanban.entities.Rol;
import org.kanban.repository.RolRepository;
import org.kanban.seguridad.modelo.RolEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolService {
	
	@Autowired
	RolRepository rolRepository;
	
	public Optional<Rol> findByRol(RolEnum rolEnum){
	 return rolRepository.findByRol(rolEnum);
	}
	public void  saveRol(Rol rol){
	rolRepository.save(rol);
		}

}
