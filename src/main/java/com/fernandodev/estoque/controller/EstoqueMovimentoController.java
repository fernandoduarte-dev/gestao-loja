package com.fernandodev.estoque.controller;

import com.fernandodev.estoque.dto.EstoqueMovimentoDTO;
import com.fernandodev.estoque.service.EstoqueMovimentoService;
import com.fernandodev.produto.dto.ProdutoItemSaldoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueMovimentoController {

    private final EstoqueMovimentoService service;

    // 🔹 ENTRADA
    @PostMapping("/entrada")
    public ResponseEntity<Void> entrada(
            @RequestParam Long produtoId,
            @RequestParam String tamanho,
            @RequestParam Integer quantidade
    ) {
        service.entrada(produtoId, tamanho, quantidade);
        return ResponseEntity.ok().build();
    }

    // 🔹 SAÍDA GENÉRICA (por quantidade)
    @PostMapping("/saida")
    public ResponseEntity<Void> saida(
            @RequestParam Long produtoId,
            @RequestParam String tamanho,
            @RequestParam Integer quantidade
    ) {
        service.saida(produtoId, tamanho, quantidade);
        return ResponseEntity.ok().build();
    }

    // 🔹 SAÍDA POR ITENS (SKU específico)
    @PostMapping("/saida-itens")
    public ResponseEntity<Void> saidaPorItens(@RequestBody List<Long> itensIds) {
        service.saidaPorItens(itensIds);
        return ResponseEntity.ok().build();
    }

    // 🔹 RESUMO DE ESTOQUE
    @GetMapping("/resumo")
    public ResponseEntity<List<ProdutoItemSaldoProjection>> resumo(
            @RequestParam(required = false) Long idProduto,
            @RequestParam(required = false) String tamanho,
            @RequestParam(required = false) String tecido,
            @RequestParam(required = false) String cor
    ) {
        return ResponseEntity.ok(
                service.buscarSaldos(idProduto, tamanho, tecido, cor)
        );
    }

    // 🔹 HISTÓRICO
    @GetMapping("/historico")
    public ResponseEntity<List<EstoqueMovimentoDTO>> historico() {
        return ResponseEntity.ok(service.historico());
    }
}