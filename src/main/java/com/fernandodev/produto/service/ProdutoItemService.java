package com.fernandodev.produto.service;

import com.fernandodev.produto.dto.ProdutoItemDTO;
import com.fernandodev.produto.entity.ProdutoItem;
import com.fernandodev.produto.repository.ProdutoItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoItemService {

    private final ProdutoItemRepository repository;

    // 🔹 Buscar TODOS os SKUs de um produto
    public List<String> buscarSkusPorProduto(Long produtoId) {
        return repository.buscarTodosPorProduto(produtoId)
                .stream()
                .map(ProdutoItem::getSku)
                .toList();
    }

    // 🔹 Buscar apenas SKUs disponíveis
    public List<ProdutoItemDTO> buscarDisponiveis(Long produtoId) {
        return repository.findByProdutoIdAndDisponivelTrue(produtoId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // 🔹 Mapper Entity → DTO
    private ProdutoItemDTO toDTO(ProdutoItem pi) {
        return new ProdutoItemDTO(
                pi.getId(),
                pi.getTamanho(),
                pi.getDisponivel()
        );
    }

    public List<String> buscarSkusDisponiveis(Long produtoId, String tamanho) {

        return repository.findDisponiveisByProdutoAndTamanho(produtoId, tamanho)
                .stream()
                .map(ProdutoItem::getSku)
                .toList();
    }
}