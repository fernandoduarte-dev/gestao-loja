package com.fernandodev.pedido.entity;

import com.fernandodev.produto.entity.Produto;
import com.fernandodev.estoque.entity.Estoque;
import com.fernandodev.infrastructure.enums.TipoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido_item")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Produto relacionado ao item
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    // Estoque específico relacionado a esse item (peça física)
    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    // Tipo do pedido (LOCACAO ou VENDA)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pedido", nullable = false)
    private TipoPedido tipoPedido;

    // Valor do item
    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
}
