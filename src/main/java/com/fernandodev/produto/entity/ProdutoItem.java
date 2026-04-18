package com.fernandodev.produto.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto_item")
public class ProdutoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private String tamanho;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private Boolean disponivel = true;
}
