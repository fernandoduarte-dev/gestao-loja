package com.fernandodev.estoque.controller;

import com.fernandodev.estoque.dto.EstoqueDTO;
import com.fernandodev.estoque.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {

    private final EstoqueService estoqueService;

    // Adicionar estoque
    @PostMapping("/adicionar")
    public ResponseEntity<EstoqueDTO> adicionarEstoque(
            @RequestParam Long produtoId,
            @RequestParam int quantidade) {
        return ResponseEntity.ok(estoqueService.adicionarEstoque(produtoId, quantidade));
    }

    // Listar todo o estoque
    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> listarEstoque() {
        return ResponseEntity.ok(estoqueService.listarEstoques());
    }

    // Buscar estoque por produtoId
    @GetMapping("/{produtoId}")
    public ResponseEntity<EstoqueDTO> buscarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.buscarPorProduto(produtoId));
    }
}
