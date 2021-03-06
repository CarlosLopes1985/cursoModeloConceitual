package com.infogrupo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.service.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotEmpty(message="Prenchimento Obrigatório")
	@Length(min=5, max=120,message="O Tamanho precisa estar entre 5 e 120 caracteres")
	private String nome;
	
	@Email(message="Email Inválido")
	@NotEmpty(message="Preenchimento Obrigatório")
	private String email;
	
	public ClienteDTO() {
		super();
	}
	
	public ClienteDTO(Cliente obj) {
		
		id = obj.getId();
		nome= obj.getNome();
		email = obj.getEmail();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
