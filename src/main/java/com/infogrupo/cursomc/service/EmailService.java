package com.infogrupo.cursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.infogrupo.cursomc.entity.Pedido;

public interface EmailService {

	void sendOrderConfirmationPedido(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
