package com.fernandodev.pedido.entity;

import com.fernandodev.infrastructure.enums.TipoPedido;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private com.fernandodev.cliente.entity.Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TipoPedido tipo; // VENDA, LOCACAO

    private LocalDate dataPedido;
    private Double valorTotal;

    // Somente usado para locação
    private LocalDate dataLocacao;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItem> itens;
}
