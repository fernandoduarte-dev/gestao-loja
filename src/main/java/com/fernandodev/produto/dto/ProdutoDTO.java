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
    private String tecido;
    private String cor;

}
