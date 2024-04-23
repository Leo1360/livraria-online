package com.fatec.livrariaonlinejpa.services;


import java.util.Optional;

import com.fatec.livrariaonlinejpa.repositories.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.Cartao;
import com.fatec.livrariaonlinejpa.model.Cliente;
import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.repositories.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final  ClienteRepository repo;
    private final CartaoRepository cartaoRepository;

    public Cliente save(Cliente cliente){
        cliente = repo.save(cliente);
        return cliente;
    }

    public Cliente findById(long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cliente not found"));

    }

    public void addEnderecoEntrega(long idCliente, Endereco enderecoEntrega){
        Cliente cliente = this.findById(idCliente);
        cliente.getEnderecosEntrega().add(enderecoEntrega);
        save(cliente);
    }

    public void removeEnderecoEntrega(long idCliente, long idEndereco) {
        Cliente cliente = findById(idCliente);
        for (Endereco e : cliente.getEnderecosEntrega()) {
            if (e.getId() == idEndereco) {
                cliente.getEnderecosEntrega().remove(e);
                break;
            }
        }
        save(cliente);
    }
    

    public void addCartao(long idCliente, Cartao cartao){
        Cliente cliente = findById(idCliente);
        cliente.getCartoes().add(cartao);
        save(cliente);
    }

    public void removeCartao(long idCliente, long idCartao){
        Cliente cliente = findById(idCliente);
        for (Cartao e : cliente.getCartoes()) {
            if (e.getId() == idCartao) {
                cliente.getCartoes().remove(e);
                break;
            }
        }
        save(cliente);
    }

    public void setCartaoPreferencial(long idCliente, long idCartao){
        Cliente cliente = this.findById(idCliente);
        Cartao cartao = cartaoRepository.getReferenceById(idCartao);
        cliente.setCartaoPreferencial(cartao);
        save(cliente);
    }

    public void update(Cliente newCliente){
        Cliente cliente = findById(newCliente.getId());
        //overwride all mutable fields
        cliente.setNome(newCliente.getNome() != null ? newCliente.getNome(): cliente.getNome());
        cliente.setDataNascimento(newCliente.getDataNascimento() != null ? newCliente.getDataNascimento() : cliente.getDataNascimento());
        cliente.setCpf(newCliente.getCpf() != null ? newCliente.getCpf() : cliente.getCpf());

        repo.save(cliente);
    }

    public void delete(long id){
        findById(id);
        repo.deleteById(id);
    }

}
