package com.fernandodev.estoque.controller;

import com.fernandodev.estoque.dto.EstoqueMovimentoDTO;
import com.fernandodev.estoque.service.EstoqueMovimentoService;
import com.fernandodev.produto.dto.ProdutoItemSaldoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueMovimentoController {

    private final EstoqueMovimentoService service;

    // 🔹 ENTRADA
    @PostMapping("/entrada")
    public void entrada(
            @RequestParam Long produtoId,
            @RequestParam String tamanho,
            @RequestParam Integer quantidade
    ) {
        service.entrada(produtoId, tamanho, quantidade);
    }

    // 🔹 SAÍDA
    @PostMapping("/saida")
    public void saida(
            @RequestParam Long produtoId,
            @RequestParam String tamanho,
            @RequestParam Integer quantidade
    ) {
        service.saida(produtoId, tamanho, quantidade);
    }

    // 🔹 RESUMO DE ESTOQUE (novo modelo)
    @GetMapping("/resumo")
    public List<ProdutoItemSaldoProjection> resumo(
            @RequestParam(required = false) Long idProduto,
            @RequestParam(required = false) String tamanho,
            @RequestParam(required = false) String tecido,
            @RequestParam(required = false) String cor
    ) {
        return service.buscarSaldos(idProduto, tamanho, tecido, cor);
    }

    // 🔹 HISTÓRICO
    @GetMapping("/historico")
    public List<EstoqueMovimentoDTO> historico() {
        return service.historico();
    }
}