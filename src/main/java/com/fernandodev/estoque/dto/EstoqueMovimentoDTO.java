package com.fernandodev.estoque.dto;

import com.fernandodev.infrastructure.enums.TipoMovimento;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record EstoqueMovimentoDTO(
        Long id,
        Long produtoId,
        String produtoNome,
        Long produtoItemId,
        TipoMovimento tipoMovimento,
        LocalDateTime data,
        String usuarioNome,
        String tamanho

) {}
