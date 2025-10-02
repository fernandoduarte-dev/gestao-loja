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

    @Column(name = "nome" , length = 50)
    private String nome;

    @Column(name = "descricao" , length = 200)
    private String descricao;

    @Column(name = "tecido" , length = 50)
    private String tecido;

    @Column(name = "tamanho" , length = 2)
    private String tamanho;

    @Column(name = "cor" , length = 50)
    private String cor;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "valor_venda", length = 10)
    private Double valorVenda;

    @Column(name = "valor_locacao", length = 10)
    private Double valorLocacao;

}
