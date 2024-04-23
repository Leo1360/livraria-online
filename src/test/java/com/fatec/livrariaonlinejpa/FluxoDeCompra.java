package com.fatec.livrariaonlinejpa;

import com.fatec.livrariaonlinejpa.model.*;
import com.fatec.livrariaonlinejpa.services.ClienteService;
import com.fatec.livrariaonlinejpa.services.PedidoService;
import com.fatec.livrariaonlinejpa.services.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FluxoDeCompra {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    ProdutoService produtoService;
    @Autowired
    ClienteService clienteService;

    @Test
    void cadastrarProduto(){
        Produto produto = new Produto();
        produto.setDescricao("O adolescente Percy Jackson descobre que os deuses gregos e as criaturas mitológicas são reais. Ele é filho de uma divindade e logo entra para um acampamento de treinamento para semideuses. Enquanto tenta se adaptar a seus novos poderes e amigos, ele descobre que o Raio-Mestre do poderoso Zeus foi roubado e ele é o principal suspeito. Assim, ele tenta solucionar o mistério junto com seus novos colegas, Grover e Annabeth.");
        produto.setNome("Percy Jacson: O ladrão de raios");
        produto.setValor(36.99f);
        produto.setPathImg("/img/livros/Percy Jackson O ladrao de raios.jpg");
        produtoService.salvar(produto);
    }
    @Test
    void salvarPedido(){
        ItemCompra item1 = new ItemCompra();
        item1.setQnt(3);
        item1.setValorUnit(36.99f);
        item1.setProduto(produtoService.findById(2));
        ItemCompra item2 = new ItemCompra();
        item2.setProduto(produtoService.findById(1));
        item2.setQnt(4);
        item2.setValorUnit(22.5f);
        List<ItemCompra> compraList = new ArrayList<>();
        compraList.add(item1);
        compraList.add(item2);


        Cliente cliente = clienteService.findById(1);
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus("Em Separacao");
        pedido.setItens(compraList);
        pedido.setEnderecoEntrega(cliente.getEnderecosEntrega().get(0));
        Pagamento pagamento = new Pagamento();
        pagamento.setCartao(cliente.getCartoes().get(0));
        pagamento.setValor(200.97f);
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pagamento);
        pedido.setPagamentoList(pagamentos);

        pedidoService.salvarNovoPedido(pedido);

    }

}
