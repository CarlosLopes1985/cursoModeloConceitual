package com.infogrupo.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.infogrupo.cursomc.dto.ClienteDTO;
import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.repositories.ClienteRepository;
import com.infogrupo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {  
		Optional<Cliente> obj = clienteRepository.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		}
	
	public Cliente update(Cliente obj) {
		
		Cliente newObj = find(obj.getId());
		
		updateData(newObj, obj);
		
		return clienteRepository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir porque há entidades relacionadas");
		}
	}
	
	public List<Cliente>findAll(){
		
		return clienteRepository.findAll();
		
	}
	
	public Page<Cliente>findPage(Integer page, Integer linesPage, String orderBy,String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPage,Direction.valueOf(direction) , orderBy);
		
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
