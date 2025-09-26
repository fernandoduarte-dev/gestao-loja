package com.fernandodev.produto.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String tamanho;
    private String cor;

    @Column(unique = true)
    private String sku;

    private Double valorVenda;
    private Double valorLocacao;
}
