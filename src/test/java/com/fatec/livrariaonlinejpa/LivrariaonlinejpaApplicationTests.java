package com.fatec.livrariaonlinejpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cidade;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.model.User;
import com.fatec.livrariaonlinejpa.services.CartaoService;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.EnderecoService;

@SpringBootTest
class LivrariaonlinejpaApplicationTests {
	@Autowired
	ClienteService clienteService;
	@Autowired
	CartaoService cartaoService;
	@Autowired
	EnderecoService enderecoService;
	private long clientId;
	private long enderecoId;
	private long cartaoId;

	@Test
	void contextLoads() {
	}

	@Test
	void salvarCliente(){
		Cliente cliente = new  Cliente();
		cliente.setNome("Carla");
		User user = new User();
		user.setEmail("carla@gmail.com");
		user.setSenha("1234");
		cliente.setUser(user);

		clienteService.save(cliente);
		this.clientId = cliente.getId();
		assertNotEquals(this.clientId, 0);
	}

	@Test
	void addEnderecoCliente(){
		Endereco endereco = new Endereco();
		Cidade cidade = new Cidade();
		cidade.setUf("SP");
		cidade.setNome("Mogi das cruzes");
		endereco.setBairro("Conjunto do bosque");
		endereco.setCep("08743080");
		endereco.setLogradouro("Rua das pedras");
		endereco.setNumero("101");
		endereco.setCidade(cidade);

		clienteService.addEnderecoEntrega(1, endereco);
		Cliente cliente = clienteService.findById(1);
		boolean find = false;
		for(Endereco end : cliente.getEnderecosEntrega()){
			if(end.getId() == 11){
				find = true;
			}
		}
		assertTrue(find);
	}

	@Test
	void addCartaoCliente(){
		Cartao cartao = new Cartao();
		cartao.setCpf("41036801802");

		clienteService.addCartao(1, cartao);
		Cliente cliente = clienteService.findById(1);
		boolean find = false;
		for(Cartao card : cliente.getCartoes()){
			if(card.getDigitos() == card.getDigitos()){
				find = true;
			}
		}
		assertTrue(find);
	}

	@Test
	void setCartaoPreferencial(){

	}

	@Test 
	void removeEnderecoEntrega(){
		clienteService.removeEnderecoEntrega(1, 1);
		Endereco endereco = enderecoService.findById(1);
		Cliente cliente = clienteService.findById(1);
		boolean find = true;
		for(Endereco end : cliente.getEnderecosEntrega()){
			if(end.getLogradouro() == endereco.getLogradouro()){
				find = false;
			}
		}
		assertTrue(find);
	}

	@Test
	void removeCartao(){
		Cartao cartao = cartaoService.findById(2);
		clienteService.removeCartao(1, 2);
		Cliente cliente = clienteService.findById(1);
		boolean find = true;
		for(Cartao card : cliente.getCartoes()){
			if(card.getDigitos() == cartao.getDigitos()){
				find = false;
			}
		}
		assertTrue(find);
	}

	@Test
	void updateCliente(){
		Cliente cliente = new  Cliente();
		cliente.setId(1);
		cliente.setNome("Carlos");
		clienteService.update(cliente);
		Cliente novo = clienteService.findById(1);
		assertEquals(novo.getNome(), "Carlos");
	}


	@Test
	void deleteCliente(){
		clienteService.delete(18);
	}


}
