package com.fernandodev.estoque.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaldoDTO {
    private Long produtoId;
    private String produtoNome;
    private String tamanho;
    private Long saldo;
    private String produtoTecido;
    private String produtoCor;
}