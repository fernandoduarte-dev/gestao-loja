package com.fernandodev.estoque.entity;

import com.fernandodev.infrastructure.enums.TipoMovimento;
import com.fernandodev.pedido.entity.Pedido;
import com.fernandodev.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estoque_movimento")
public class EstoqueMovimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimento", nullable = false)
    private TipoMovimento tipoMovimento; // ENTRADA ou SAIDA

    @Column(nullable = false)
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido; // null se for entrada sem pedido

    @Column(nullable = false)
    private LocalDateTime dataMovimento;
}
