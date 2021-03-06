package com.infogrupo.cursomc.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.infogrupo.cursomc.entity.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// Responsável por obter o valor dessa chave do propertie
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaEmailSender;
	
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
	
	protected String htmlFromTemplatePedido(Pedido obj) {
		
		Context context = new Context();
		
		context.setVariable("pedido", obj);
		
		return templateEngine.process("email/confirmacaoPedido", context);
		
	}
	
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
			
		} catch (Exception e) {
			sendOrderConfirmationPedido(obj);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		
		MimeMessage mm = javaEmailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido Confirmado! Codigo: "+obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj),true);
		
		return mm;
	}
}
