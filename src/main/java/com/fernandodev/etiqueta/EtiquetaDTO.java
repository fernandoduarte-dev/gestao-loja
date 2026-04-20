package com.fernandodev.etiqueta;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EtiquetaDTO {

    private Long produtoId;
    private String produtoNome;

    private String sku;
    private String tamanho;

    private boolean disponivel;

    private LocalDateTime data; // 🔥 ESSENCIAL
}