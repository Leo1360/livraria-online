package com.fatec.livrariaonlinejpa.services;

import com.fatec.livrariaonlinejpa.dto.AddCarrinhoItemDTO;
import com.fatec.livrariaonlinejpa.dto.EnderecoDTO;
import com.fatec.livrariaonlinejpa.model.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrinhoService {
    private final CupomService cupomService;
    private final ProdutoService produtoService;
    private final CartaoService cartaoService;
    private final ClienteService clienteService;

    public void calcularPedido(Pedido pedido){
        calcularDesconto(pedido);
        calcularFreter(pedido);
        calcularSubtotal(pedido);
        calcularTotal(pedido);

    }

    public void calcularDesconto(Pedido pedido) {
        pedido.getCupons().stream()
                .map(Cupom::getDesconto)
                .reduce(BigDecimal::add)
                .ifPresentOrElse(pedido::setDesconto,() -> pedido.setDesconto(new BigDecimal(0)));

    }

    public void calcularSubtotal(Pedido pedido){
        pedido.getItens().stream()
                .map(item -> item.getValorUnit().multiply(new BigDecimal(item.getQnt())))
                .reduce(BigDecimal::add)
                .ifPresentOrElse(pedido::setSubTotal,() -> pedido.setSubTotal(new  BigDecimal(0)));

    }

    public void calcularFreter(Pedido pedido){
        BigDecimal frete = new BigDecimal("2.13");
        Integer qntItens = pedido.getItens().stream()
                .map(ItemCompra::getQnt)
                .reduce(Integer::sum)
                .orElse(0);

        frete = frete.multiply(new BigDecimal(qntItens));
        pedido.setFrete(frete);
    }

    public void calcularTotal(Pedido pedido){
        if(pedido.getDesconto().compareTo(pedido.getSubTotal()) > 0){
            pedido.setTotal(pedido.getFrete());
            return;
        }
        BigDecimal total = pedido.getFrete()
                .add(pedido.getSubTotal())
                .subtract(pedido.getDesconto());

        pedido.setTotal(total);
    }


    public void removerCupom(Pedido pedido, long id){
        for(Cupom cupom: pedido.getCupons()){
            if(cupom.getId() == id){
                pedido.getCupons().remove(cupom);
                calcularPedido(pedido);
                return;
            }
        }

    }

    public void addCupom(Pedido pedido, String nomeCupom){
        if(! cupomService.existsByName(nomeCupom)) {
            return;
        }

        Cupom cupom = cupomService.findByNome(nomeCupom);

        if(! cupomService.validarCupom(cupom,pedido.getCliente().getId())){
            return;
        }
        pedido.getCupons().add(cupom);
        calcularPedido(pedido);

    }

    public void addItem(Pedido pedido, ItemCompra novoItem){
        pedido.getItens().stream()
                .filter(item -> item.getId() == novoItem.getId())
                .findFirst()
                .ifPresent(pedido::addItem);

        calcularPedido(pedido);
    }

    public void addItem(Pedido pedido, AddCarrinhoItemDTO itemDto){
        ItemCompra novoItem = new ItemCompra();
        Produto produto = produtoService.findById(itemDto.getId());
        novoItem.setProduto(produto);
        novoItem.setQnt(itemDto.getQnt());
        novoItem.setValorUnit(produto.getValor());
         Optional<ItemCompra> optItem = pedido.getItens().stream()
                .filter(item -> item.getProduto().getId() == produto.getId())
                .findFirst();
        if(optItem.isEmpty()){
            pedido.getItens().add(novoItem);

        }

        calcularPedido(pedido);
    }

    public void editQntItem(Pedido pedido, ItemCompra novoItem){

        for(ItemCompra item : pedido.getItens()){
            if(item.getProduto().getId() == novoItem.getId()){
                item.setQnt(novoItem.getQnt());
            }
        }

        calcularPedido(pedido);
    }

    public void removerItem(Pedido pedido, long id){
        for(ItemCompra item : pedido.getItens()){
            if(item.getProduto().getId() == id){
                pedido.getItens().remove(item);
                calcularPedido(pedido);
                return;
            }
        }


    }

    public void setEndereco(Pedido pedido, EnderecoDTO endDTO){
        Optional<Endereco> endereco = pedido.getCliente().getEnderecosEntrega().stream()
                .filter(e -> e.getId() == endDTO.getId()).findFirst();

        if(endereco.isPresent()){
            pedido.setEnderecoEntrega(endereco.get());
        }

        calcularPedido(pedido);
    }

    public void removeCartao(Pedido pedido, long id){
        for(Pagamento pag: pedido.getPagamentoList()){
            if(pag.getCartao().getId() == id){
                pedido.getPagamentoList().remove(pag);
                return;
            }
        }

    }

    public void addCartao(Pedido pedido, long cartaoId){

        for(Pagamento pag : pedido.getPagamentoList()){
            if(pag.getCartao().getId() == cartaoId){
                return;
            }
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setCartao(cartaoService.findById(cartaoId));

        if(pedido.getPagamentoList().isEmpty()){
            pagamento.setValor(pedido.getTotal());
        }
        pedido.addPagamento(pagamento);

        calcularPedido(pedido);

    }

    public void editarPagamento(Pedido pedido, BigDecimal valor, long id){
        for(Pagamento pagamento : pedido.getPagamentoList()){
            if (pagamento.getCartao().getId() == id){
                pagamento.setValor(valor);
                calcularPedido(pedido);
            }
        }
    }

    public Pedido getCarrinho(Pedido pedido, long clientId){

        if(pedido == null){
            return inicializarCarrinho(clientId);
        }else{
            if(pedido.getPagamentoList().size() == 1){
                pedido.getPagamentoList().get(0).setValor(pedido.getTotal());
            }
        }
        return pedido;

    }

    public Pedido inicializarCarrinho(long clientId){
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.findById(clientId);
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(pedido.getCliente().getEnderecoPreferencial());

        Pagamento pag = new Pagamento();
        pag.setCartao(pedido.getCliente().getCartaoPreferencial());
        pedido.addPagamento(pag);
        return pedido;
    }

    public List<Produto> getRecomendacoes(List<ItemCompra> itens){
        List<Produto> compras = itens.stream().map(ItemCompra::getProduto).collect(Collectors.toList());
        List<Produto> livrosDaCat = produtoService.findAll();
        for(Produto prd: compras){
            for(Produto prod:livrosDaCat){
                if(prod.getId() == prd.getId()){
                    livrosDaCat.remove(prod);
                    break;
                }
            }
        }
        String body = getBody(compras, livrosDaCat);

        Optional<String> optReponse = sendRequestGPT(body);

        List<Produto> resultList = new ArrayList<>();
        if(optReponse.isPresent()){
            JSONObject json = new JSONObject(optReponse.get());

            String res = json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");

            String[] livros = res.split(";");
            resultList =  livrosDaCat.stream()
                    .filter(l -> Arrays.stream(livros)
                            .anyMatch(string -> Long.parseLong(string) == l.getId()))
                    .toList();
        }
        return resultList;
    }

    private static Optional<String> sendRequestGPT(String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer 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")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            return Optional.of(client.send(request, HttpResponse.BodyHandlers.ofString()).body());
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }

    }

    private String getBody(List<Produto> compras, List<Produto> livrosDaCat){
        StringBuilder livros = new StringBuilder();
        for(Produto prod: compras){
            livros.append(prod.getNome()).append(",");
        }

        StringBuilder opcoes = new StringBuilder();
        for(Produto prod: livrosDaCat){
            opcoes.append(prod.getId()).append(" - ").append(prod.getNome()).append(";\\n ");
        }
        String content = opcoes + "\\n Os livros citados acima estão acompanhados de sua numeração, que esta dentro dos parênteses.\\n Dados todos os livros citados a cima, retorne os 5 títulos que mais se conectam e fazem sentido serem comprados juntos com os livros '" + livros + "'.\\n Responda apenas as numerações sem parênteses e separadas por ';' sem nenhuma explicação dos motivos, apenas os numeros.";

        StringBuilder body =  new StringBuilder();
        body.append("""
                {
                    "model": "gpt-4o",
                    "messages": [
                      {
                        "role": "user",
                        "content": \"""");
        body.append(content).append("\"}]}");
        return body.toString();


    }


}
