package com.fernandodev.infrastructure.converter;

import com.fernandodev.pedido.dto.PedidoDTO;
import com.fernandodev.pedido.dto.PedidoItemDTO;
import com.fernandodev.pedido.entity.Pedido;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@UtilityClass
public class PedidoConverter {

    public PedidoDTO paraPedidoDTO(Pedido pedido) {
        if (pedido == null) return null;

        return PedidoDTO.builder()
                .id(pedido.getId())
                .clienteId(pedido.getCliente().getId())
                .tipoPedido(pedido.getTipoPedido())
                .valorTotal(pedido.getValorTotal())
                .dataEvento(pedido.getDataEvento())
                .dataRetirada(pedido.getDataRetirada())
                .dataDevolucao(pedido.getDataDevolucao())
                .itens(
                        pedido.getItens().stream()
                                .map(item -> PedidoItemDTO.builder()
                                        .produtoId(item.getProduto().getId())
                                        .sku(item.getProduto().getSku())
                                        .quantidade(item.getQuantidade())
                                        .valorUnitario(item.getValor())
                                        .valorTotal(item.getValor().multiply(BigDecimal.valueOf(item.getQuantidade())))
                                        .build()
                                )
                                .collect(Collectors.toList())
                )
                .build();
    }

    public Pedido paraPedido(PedidoDTO pedidoDTO) {

        Pedido pedido = Pedido.builder()
                .id(pedidoDTO.getId())
                .tipoPedido(pedidoDTO.getTipoPedido())
                .valorTotal(pedidoDTO.getValorTotal())
                .dataEvento(pedidoDTO.getDataEvento())
                .dataRetirada(pedidoDTO.getDataRetirada())
                .dataDevolucao(pedidoDTO.getDataDevolucao())
                .build();

        // Os itens podem ser adicionados depois no Service
        return pedido;
    }
}
