package com.infogrupo.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogrupo.cursomc.entity.Categoria;
import com.infogrupo.cursomc.repositories.CategoriaRepository;
import com.infogrupo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Categoria find(Integer id) {  
		Optional<Categoria> obj = categoriaRepository.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName())); 
		}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		
		return categoriaRepository.save(obj);
}
}