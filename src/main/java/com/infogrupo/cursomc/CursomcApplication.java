package com.infogrupo.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.infogrupo.cursomc.entity.Categoria;
import com.infogrupo.cursomc.entity.Cidade;
import com.infogrupo.cursomc.entity.Cliente;
import com.infogrupo.cursomc.entity.Endereco;
import com.infogrupo.cursomc.entity.Estado;
import com.infogrupo.cursomc.entity.Pagamento;
import com.infogrupo.cursomc.entity.PagamentoComBoleto;
import com.infogrupo.cursomc.entity.PagamentoComCartão;
import com.infogrupo.cursomc.entity.Pedido;
import com.infogrupo.cursomc.entity.Produto;
import com.infogrupo.cursomc.entity.enums.EstadoPagamento;
import com.infogrupo.cursomc.entity.enums.TipoCliente;
import com.infogrupo.cursomc.repositories.CategoriaRepository;
import com.infogrupo.cursomc.repositories.CidadeRepository;
import com.infogrupo.cursomc.repositories.ClienteRepository;
import com.infogrupo.cursomc.repositories.EnderecoRepository;
import com.infogrupo.cursomc.repositories.EstadoRepository;
import com.infogrupo.cursomc.repositories.PagamentoRepository;
import com.infogrupo.cursomc.repositories.PedidoRepository;
import com.infogrupo.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
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
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",2000.00);
		Produto p3 = new Produto(null,"Mouse",2000.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		Estado est1 = new Estado(null, "Minas Gerais"); 
		Estado est2 = new Estado(null, "São paulo");
		
		Cidade c1 = new Cidade(null, "Urbelandia", est1);
		Cidade c2 = new Cidade(null, "São paulo", est2);
		Cidade c3 = new Cidade(null, "campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Carlos", "caka19@hotmail.com", "11792993706", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("33587928","981759533"));
		
		Endereco end1 = new Endereco(null, "Rua Flores", "300", "ap303", "Anchieta", "21635440", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Camélia", "400", "ap305", "Valqueire", "21635440", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1,end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1,end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2015 17:32"), cli1, end2);
		
		Pagamento pag1 = new PagamentoComCartão(null, EstadoPagamento.QUITADA, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 =  new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 18:35"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pag1,pag2));
	}

}
