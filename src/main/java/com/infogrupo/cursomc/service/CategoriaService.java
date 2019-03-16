package com.infogrupo.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		
		return categoriaRepository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir uma categoria que tenha produtos");
		}
	}
	
	public List<Categoria>findAll(){
		
		return categoriaRepository.findAll();
		
	}
	
	public Page<Categoria>findPage(Integer page, Integer linesPage, String orderBy,String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPage,Direction.valueOf(direction) , orderBy);
		
		return categoriaRepository.findAll(pageRequest);
	}
}