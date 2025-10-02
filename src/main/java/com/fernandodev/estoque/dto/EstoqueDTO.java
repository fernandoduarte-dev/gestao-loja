package com.fernandodev.estoque.dto;

import com.fernandodev.infrastructure.enums.StatusEstoque;
import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.produto.entity.Produto;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EstoqueDTO {

    private Long id;
    private ProdutoDTO produto;

    @Builder.Default
    private StatusEstoque status = StatusEstoque.DISPONIVEL;

    private Integer quantidade;
}
