package com.fernandodev.estoque.dto;

import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EstoqueMovimentoDTO {

    private Long id;
    private ProdutoDTO produto;
    private TipoMovimento tipoMovimento; // ENTRADA ou SAIDA
    private Integer quantidade;
    private LocalDateTime dataMovimento;
}
