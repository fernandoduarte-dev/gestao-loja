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
                .cor(produto.getCor())
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
                .cor(produtoDTO.getCor())
                .tecido(produtoDTO.getTecido())
                .build();
    }
}
