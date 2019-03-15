package com.infogrupo.cursomc.entity;

import javax.persistence.Entity;

import com.infogrupo.cursomc.entity.enums.EstadoPagamento;

@Entity
public class PagamentoComCartão extends Pagamento {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartão() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagamentoComCartão(Integer id, EstadoPagamento estado, Pedido pedido,Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
		// TODO Auto-generated constructor stub
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
}
