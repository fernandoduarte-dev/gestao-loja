package com.fernandodev.produto.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SkuGenerator {

    private final JdbcTemplate jdbcTemplate;

    public SkuGenerator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String gerar(String tipo, String tecido, String cor, String tamanho) {

        Long seq = jdbcTemplate.queryForObject(
                "SELECT nextval('produto_item_sku_seq')",
                Long.class
        );

        return formatar(tipo) + "-" +
                formatar(tecido) + "-" +
                formatar(cor) + "-" +
                tamanho + "-" +
                String.format("%06d", seq);
    }

    private String formatar(String v) {
        if (v == null || v.isBlank()) return "NA";
        return v.substring(0, Math.min(3, v.length())).toUpperCase();
    }
}