package com.fernandodev.produto.controller;

import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    // ✅ CRIAR PRODUTO
    @PostMapping
    public ProdutoDTO salvar(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.salvarProduto(produtoDTO);
    }

    // ✅ LISTAR / FILTRAR PRODUTOS
    @GetMapping("/buscar")
    public List<ProdutoDTO> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cor,
            @RequestParam(required = false) String tecido
    ) {
        return produtoService.buscarProdutos(nome, cor, tecido);
    }

    // ✅ BUSCAR POR ID
    @GetMapping("/id/{id}")
    public ProdutoDTO buscarPorId(@PathVariable Long id) {
        return produtoService.buscarPorId(id);
    }

    // ✅ DELETAR PRODUTO
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoService.deletarProduto(id);
    }
}