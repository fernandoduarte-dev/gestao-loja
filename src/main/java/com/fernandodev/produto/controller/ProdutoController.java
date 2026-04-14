package com.fernandodev.produto.controller;

import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    // Criar cliente
    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastraProduto(@RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.salvarProduto(produtoDTO));
    }

    // Buscar Produto
    @GetMapping("/buscar")
    public List<ProdutoDTO> buscarProdutos(
            @RequestParam(required = false) String sku,
            @RequestParam(required = false) String tamanho,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) String tecido
    ) {
        return produtoService.buscarProdutos(sku, tamanho, cor, tecido);
    }

    // Deletar cliente por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoId(@PathVariable Long id) {
        produtoService.deletaProduto(id);
        return ResponseEntity.noContent().build();
    }




}