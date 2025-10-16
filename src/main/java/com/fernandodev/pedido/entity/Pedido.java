package com.fernandodev.pedido.entity;

import com.fernandodev.cliente.entity.Cliente;
import com.fernandodev.infrastructure.enums.StatusPedido;
import com.fernandodev.infrastructure.enums.TipoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pedido")
    private TipoPedido tipoPedido; // VENDA ou LOCACAO

    @Builder.Default
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens = new ArrayList<>();

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    @Column(name = "data_locacao")
    private LocalDate dataLocacao;

    @Column(name = "data_retirada")
    private LocalDate dataRetirada;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;
}
