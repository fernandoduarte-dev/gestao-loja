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
                .cor(produto.getCor())
                .tecido(produto.getTecido())
                .build();
    }

    public Produto paraProduto(ProdutoDTO produtoDTO) {
        if (produtoDTO == null) return null;

        return Produto.builder()
                .id(produtoDTO.getId())
                .nome(produtoDTO.getNome())
                .cor(produtoDTO.getCor())
                .tecido(produtoDTO.getTecido())
                .build();
    }
}
