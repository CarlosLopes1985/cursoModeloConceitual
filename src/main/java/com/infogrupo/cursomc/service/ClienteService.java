package com.infogrupo.cursomc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infogrupo.cursomc.dto.ClienteDTO;
import com.infogrupo.cursomc.dto.ClienteNewDTO;
import com.infogrupo.cursomc.entity.Cidade;
import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.entity.Endereco;
import com.infogrupo.cursomc.entity.enums.Perfil;
import com.infogrupo.cursomc.entity.enums.TipoCliente;
import com.infogrupo.cursomc.repositories.ClienteRepository;
import com.infogrupo.cursomc.security.UserSS;
import com.infogrupo.cursomc.service.exceptions.AuthorizationException;
import com.infogrupo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) {  
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasRole(Perfil.ADMIN)&& !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = clienteRepository.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName())); 
		}
	
	public Cliente update(Cliente obj) {
		
		Cliente newObj = find(obj.getId());
		
		updateData(newObj, obj);
		
		return clienteRepository.save(newObj);
	}
	
	@Transactional	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		
		return clienteRepository.save(obj);
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
		
		return new Cliente(objDTO.getId(),objDTO.getNome(),objDTO.getEmail(),null,null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		
		Cliente  cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade   cid = new Cidade(objDto.getIdCidade(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
}
