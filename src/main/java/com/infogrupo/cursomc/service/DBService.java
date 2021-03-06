package com.infogrupo.cursomc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infogrupo.cursomc.entity.Categoria;
import com.infogrupo.cursomc.entity.Cidade;
import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.entity.Endereco;
import com.infogrupo.cursomc.entity.Estado;
import com.infogrupo.cursomc.entity.ItemPedido;
import com.infogrupo.cursomc.entity.Pagamento;
import com.infogrupo.cursomc.entity.PagamentoComBoleto;
import com.infogrupo.cursomc.entity.PagamentoComCartao;
import com.infogrupo.cursomc.entity.Pedido;
import com.infogrupo.cursomc.entity.Produto;
import com.infogrupo.cursomc.entity.enums.EstadoPagamento;
import com.infogrupo.cursomc.entity.enums.Perfil;
import com.infogrupo.cursomc.entity.enums.TipoCliente;
import com.infogrupo.cursomc.repositories.CategoriaRepository;
import com.infogrupo.cursomc.repositories.CidadeRepository;
import com.infogrupo.cursomc.repositories.ClienteRepository;
import com.infogrupo.cursomc.repositories.EnderecoRepository;
import com.infogrupo.cursomc.repositories.EstadoRepository;
import com.infogrupo.cursomc.repositories.ItemPedidoRepository;
import com.infogrupo.cursomc.repositories.PagamentoRepository;
import com.infogrupo.cursomc.repositories.PedidoRepository;
import com.infogrupo.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	
	public void InstantiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Perfumaria");
		Categoria cat5 = new Categoria(null, "Eletronicos");
		Categoria cat6 = new Categoria(null, "Eletrodomesticos");
		Categoria cat7 = new Categoria(null, "Celular");
		Categoria cat8 = new Categoria(null, "Roupas");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 2000.00);
		Produto p3 = new Produto(null, "Mouse", 2000.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3,cat4,cat5,cat6,cat7,cat8));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São paulo");

		Cidade c1 = new Cidade(null, "Urbelandia", est1);
		Cidade c2 = new Cidade(null, "São paulo", est2);
		Cidade c3 = new Cidade(null, "campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Carlos", "caka19@gmail.com", "11792993706", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("33587928", "981759533"));

		Cliente cli2 = new Cliente(null, "Natasha", "natacha@gmail.com", "31628382740", TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.getTelefones().addAll(Arrays.asList("33587915", "981758855"));
		cli2.addPerfil(Perfil.ADMIN);
		
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "ap303", "Anchieta", "21635440", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Camélia", "400", "ap305", "Valqueire", "21635440", cli1, c2);
		Endereco end3 = new Endereco(null, "Rua do Valão", "680", "ap901", "Campo Grande", "21635440", cli2, c2);

		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		cli2.getEnderecos().addAll(Arrays.asList(end3));
		
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2015 17:32"), cli1, end2);

		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADA, ped1, 6);
		ped1.setPagamento(pag1);

		Pagamento pag2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 18:35"),
				null);
		ped2.setPagamento(pag2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));

		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		itemPedidoRepository.saveAll(Arrays.asList(ip1));
	}
}
