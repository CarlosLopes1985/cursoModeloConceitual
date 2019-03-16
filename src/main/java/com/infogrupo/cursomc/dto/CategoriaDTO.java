package com.infogrupo.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.infogrupo.cursomc.entity.Categoria;

public class CategoriaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	@NotEmpty(message="Prenchimento Obrigatório")
	@Length(min=5, max=80,message="O Tamanho precisa estar entre 5 e 90 caracteres")
	private String nome;
	
	public CategoriaDTO() {
		super();
	}

	public CategoriaDTO(Categoria obj) {
		super();
		 id   = obj.getId(); 
		 nome = obj.getNome();
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
}
