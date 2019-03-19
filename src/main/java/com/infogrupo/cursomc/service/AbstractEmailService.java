package com.infogrupo.cursomc.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.infogrupo.cursomc.entity.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// Responsável por obter o valor dessa chave do propertie
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationPedido(Pedido obj) {
		SimpleMailMessage msg = prepareSimpleMailMessagefromPedido(obj);
		sendEmail(msg);
		
	}

	protected  SimpleMailMessage prepareSimpleMailMessagefromPedido(Pedido obj) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado! Código: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		
		return sm;
		
	}
}
