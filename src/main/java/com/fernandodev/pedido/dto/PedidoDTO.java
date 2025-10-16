package com.fernandodev.pedido.dto;

import com.fernandodev.infrastructure.enums.TipoPedido;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private List<PedidoItemDTO> itens;
    private TipoPedido tipoPedido;
    private BigDecimal valorTotal;
    private LocalDate dataEvento;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
}
