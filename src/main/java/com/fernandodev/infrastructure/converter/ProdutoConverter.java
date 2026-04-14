package com.fernandodev.infrastructure.converter;

import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.produto.entity.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoConverter {

    public ProdutoDTO paraProdutoDTO(Produto produto) {
        if (produto == null) return null;

        return ProdutoDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .descricao(produto.getDescricao())
                .valorLocacao(produto.getValorLocacao())
                .valorVenda(produto.getValorVenda())
                .sku(produto.getSku())
                .cor(produto.getCor())
                .tamanho(produto.getTamanho())
                .tecido(produto.getTecido())
                .build();
    }

    public Produto paraProduto(ProdutoDTO produtoDTO) {
        if (produtoDTO == null) return null;

        return Produto.builder()
                .id(produtoDTO.getId())
                .nome(produtoDTO.getNome())
                .descricao(produtoDTO.getDescricao())
                .valorLocacao(produtoDTO.getValorLocacao())
                .valorVenda(produtoDTO.getValorVenda())
                .sku(produtoDTO.getSku())
                .cor(produtoDTO.getCor())
                .tamanho(produtoDTO.getTamanho())
                .tecido(produtoDTO.getTecido())
                .build();
    }
}
