package com.fernandodev.produto.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProdutoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String tecido;
    private String tamanho;
    private String cor;
    private String sku;
    private Double valorVenda;
    private Double valorLocacao;
}
