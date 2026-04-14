package com.fernandodev.estoque.dto;

import com.fernandodev.produto.dto.ProdutoDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EstoqueDTO {

    private Long id;
    private ProdutoDTO produto;
    private Integer quantidadeAtual;
}
