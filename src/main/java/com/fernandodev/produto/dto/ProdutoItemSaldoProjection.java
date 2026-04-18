package com.fernandodev.produto.dto;

public interface ProdutoItemSaldoProjection {

    Long getProdutoId();

    String getProdutoNome();

    String getProdutoTecido();

    String getProdutoCor();

    String getTamanho();

    Long getSaldo();
}