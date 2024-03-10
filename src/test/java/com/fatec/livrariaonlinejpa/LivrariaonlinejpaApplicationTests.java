package com.fatec.livrariaonlinejpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cidade;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.model.User;
import com.fatec.livrariaonlinejpa.services.ClienteService;

@SpringBootTest
class LivrariaonlinejpaApplicationTests {
	@Autowired
	ClienteService clienteService;

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
	}

	@Test
	void addCartaoCliente(){
		Cartao cartao = new Cartao();
		cartao.setBandeira("visa");
		cartao.setDigitosFinais("1548");
		cartao.setCpf("41036801802");
		cartao.setToken("65191sd951as9d51as95d1");

		clienteService.addCartao(1, cartao);
	}

	@Test
	void setCartaoPreferencial(){
		clienteService.setCartaoPreferencial(1, 1);
	}

	@Test 
	void removeEnderecoEntrega(){
		clienteService.removeEnderecoEntrega(1, 1);
	}

	@Test
	void removeCartao(){
		clienteService.removeCartao(1, 2);
	}

	@Test
	void updateCliente(){
		Cliente cliente = new  Cliente();
		cliente.setId(1);
		cliente.setNome("Carlos");
		clienteService.update(cliente);
	}


	@Test
	void deleteCliente(){
		clienteService.delete(2);
	}


}
