package com.fatec.livrariaonlinejpa.dto;

import com.fatec.livrariaonlinejpa.model.Endereco;
import com.fatec.livrariaonlinejpa.services.EnderecoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnderecoDTO {
    private long id;

}
