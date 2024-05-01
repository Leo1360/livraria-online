package com.fatec.livrariaonlinejpa.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.repositories.EnderecoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnderecoService {
private final EnderecoRepository repo;

    public Endereco save(Endereco endereco){
        return repo.save(endereco);
    }

    public Endereco update(Endereco novoEndereco){
        Endereco antigoEndereco = findById(novoEndereco.getId());
        // Mergin updated fields (if any field is blank , the old value remains)
        if(!novoEndereco.getLogradouro().isBlank()){antigoEndereco.setLogradouro(novoEndereco.getLogradouro());}
        if(!novoEndereco.getBairro().isBlank()){antigoEndereco.setBairro(novoEndereco.getBairro());}
        if(!novoEndereco.getCep().isBlank()){antigoEndereco.setCep(novoEndereco.getCep());}
        if(!novoEndereco.getComplemento().isBlank()){antigoEndereco.setComplemento(novoEndereco.getComplemento());}
        if(!novoEndereco.getNumero().isBlank()){antigoEndereco.setNumero(novoEndereco.getNumero());}
        if(!novoEndereco.getCidade().getNome().isBlank()){antigoEndereco.getCidade().setNome(novoEndereco.getCidade().getNome());}
        if(!novoEndereco.getCidade().getUf().isBlank()){antigoEndereco.getCidade().setUf(novoEndereco.getCidade().getUf());}

        return repo.save(antigoEndereco);
    }

    public  Endereco findById(long id){
        return repo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Endereco not found"));
    }

    public void delete(long id){
        if (!repo.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }
        repo.deleteById(id);
    }
    

}
