package com.infogrupo.cursomc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogrupo.cursomc.entity.Pedido;
import com.infogrupo.cursomc.repositories.PedidoRepository;
import com.infogrupo.cursomc.service.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido find(Integer id) {  
		Optional<Pedido> obj = pedidoRepository.findById(id);  
		return obj.orElseThrow(() -> new ObjectNotFoundException(    
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName())); }
}
