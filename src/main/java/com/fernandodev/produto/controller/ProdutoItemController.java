package com.fernandodev.produto.controller;


import com.fernandodev.produto.dto.ProdutoItemDTO;
import com.fernandodev.produto.entity.ProdutoItem;
import com.fernandodev.produto.repository.ProdutoItemRepository;
import com.fernandodev.produto.service.ProdutoItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/produto-item")
@RequiredArgsConstructor
public class ProdutoItemController {

    private final ProdutoItemRepository produtoItemRepository;
    private final ProdutoItemService produtoItemService;

    @GetMapping("/disponiveis")
    public List<ProdutoItem> listarDisponiveis(@RequestParam Long produtoId) {
        return produtoItemRepository.findByProdutoIdAndDisponivelTrue(produtoId);
    }

    @GetMapping("/{produtoId}/skus")
    public List<String> buscarSkusDisponiveis(
            @PathVariable Long produtoId,
            @RequestParam(required = false) String tamanho
    ) {
        return produtoItemService.buscarSkusDisponiveis(produtoId, tamanho);
    }
}