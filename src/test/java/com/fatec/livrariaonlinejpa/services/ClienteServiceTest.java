package com.fatec.livrariaonlinejpa.services;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class ClienteServiceTest {
    private final ClienteService clienteService;

    @Test
    void save() {
    }

    @Test
    void findById() {
    }

    @Test
    void addEnderecoEntrega() {
    }

    @Test
    void removeEnderecoEntrega() {
    }

    @Test
    void addCartao() {
    }

    @Test
    void removeCartao() {
    }

    @Test
    void setCartaoPreferencial() {
        clienteService.setCartaoPreferencial(1,7);
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}