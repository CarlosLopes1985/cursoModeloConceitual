package com.infogrupo.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.infogrupo.cursomc.dto.ClienteNewDTO;
import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.entity.enums.TipoCliente;
import com.infogrupo.cursomc.repositories.ClienteRepository;
import com.infogrupo.cursomc.service.exceptions.FieldMessage;
import com.infogrupo.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		  
		List<FieldMessage> list = new ArrayList<>(); 
		
		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email","Email já existente"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","Cpf inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj","Cnpj inválido"));
		}
		
		 for (FieldMessage e : list) {   
			 context.disableDefaultConstraintViolation();    
			 context.buildConstraintViolationWithTemplate(e.getMessage())            
			 .addPropertyNode(e.getFieldName()).addConstraintViolation();    
			 } 
		 return list.isEmpty();     }
	}


