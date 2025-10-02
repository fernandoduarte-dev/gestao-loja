package com.fernandodev.estoque.entity;

import com.fernandodev.infrastructure.enums.StatusEstoque;
import com.fernandodev.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private StatusEstoque status = StatusEstoque.DISPONIVEL;

    @Column(name = "quantidade")
    private Integer quantidade;
}
