package com.fernandodev.pedido.service;

import com.fernandodev.cliente.entity.Cliente;
import com.fernandodev.cliente.repository.ClienteRepository;
import com.fernandodev.estoque.entity.Estoque;
import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import com.fernandodev.estoque.repository.EstoqueRepository;
import com.fernandodev.estoque.repository.EstoqueMovimentoRepository;
import com.fernandodev.infrastructure.enums.StatusPedido;
import com.fernandodev.pedido.dto.PedidoDTO;
import com.fernandodev.pedido.dto.PedidoItemDTO;
import com.fernandodev.pedido.entity.Pedido;
import com.fernandodev.pedido.entity.PedidoItem;
import com.fernandodev.pedido.repository.PedidoRepository;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;
    private final EstoqueMovimentoRepository estoqueMovimentoRepository;

    @Transactional
    public Pedido gerarTipoPedido(PedidoDTO pedidoDTO) {
        validarTipoPedido(pedidoDTO);

        Cliente cliente = buscarCliente(pedidoDTO.getClienteId());
        Pedido pedido = criarPedido(pedidoDTO, cliente);

        BigDecimal total = BigDecimal.ZERO;

        for (PedidoItemDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = buscarProduto(itemDTO);
            int quantidade = itemDTO.getQuantidade() != null ? itemDTO.getQuantidade() : 1;

            Estoque estoque = estoqueRepository.findByProduto(produto)
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto: " + produto.getNome()));

            if (estoque.getQuantidadeAtual() < quantidade) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            BigDecimal valorUnitario = obterValorUnitario(itemDTO, produto);

            for (int i = 0; i < quantidade; i++) {
                PedidoItem item = PedidoItem.builder()
                        .produto(produto)
                        .tipoPedido(pedido.getTipoPedido())
                        .valor(valorUnitario)
                        .quantidade(1)
                        .pedido(pedido)
                        .build();
                pedido.getItens().add(item);
            }

            total = total.add(valorUnitario.multiply(BigDecimal.valueOf(quantidade)));

            // Atualiza quantidade do estoque
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() - quantidade);
            estoqueRepository.save(estoque);

            // Cria registro de saída
            EstoqueMovimento movimento = EstoqueMovimento.builder()
                    .produto(produto)
                    .tipoMovimento(TipoMovimento.SAIDA)
                    .quantidade(quantidade)
                    .pedido(pedido)
                    .dataMovimento(LocalDateTime.now())
                    .build();
            estoqueMovimentoRepository.save(movimento);
        }

        pedido.setValorTotal(total);
        pedido.setStatus(StatusPedido.EM_ANDAMENTO);

        return pedidoRepository.save(pedido);
    }

    // ------------------- MÉTODOS AUXILIARES -------------------

    private void validarTipoPedido(PedidoDTO pedidoDTO) {
        if (pedidoDTO.getTipoPedido() == null) {
            throw new IllegalArgumentException(
                    "O tipo de pedido não pode ser nulo. Valores válidos: LOCACAO, VENDA."
            );
        }
    }

    private Cliente buscarCliente(Long clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + clienteId));
    }

    private Pedido criarPedido(PedidoDTO pedidoDTO, Cliente cliente) {
        return Pedido.builder()
                .cliente(cliente)
                .tipoPedido(pedidoDTO.getTipoPedido())
                .status(StatusPedido.PENDENTE)
                .valorTotal(BigDecimal.ZERO)
                .dataEvento(pedidoDTO.getDataEvento())
                .dataRetirada(pedidoDTO.getDataRetirada())
                .dataDevolucao(pedidoDTO.getDataDevolucao())
                .itens(new ArrayList<>())
                .build();
    }

    private Produto buscarProduto(PedidoItemDTO itemDTO) {
        if (itemDTO.getSku() != null && !itemDTO.getSku().isBlank()) {
            return produtoRepository.findBySku(itemDTO.getSku())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado para SKU: " + itemDTO.getSku()));
        } else if (itemDTO.getNome() != null && !itemDTO.getNome().isBlank()) {
            return produtoRepository.findByNome(itemDTO.getNome())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com nome: " + itemDTO.getNome()));
        } else {
            throw new IllegalArgumentException("Informe SKU ou nome do produto.");
        }
    }

    private BigDecimal obterValorUnitario(PedidoItemDTO itemDTO, Produto produto) {
        if (itemDTO.getValorUnitario() != null) {
            return itemDTO.getValorUnitario();
        }
        if (produto.getValorLocacao() == null) {
            throw new IllegalArgumentException("Produto sem valor de locação definido: " + produto.getNome());
        }
        return BigDecimal.valueOf(produto.getValorLocacao());
    }

    // ------------------- MÉTODO PARA DEVOLUÇÃO DE LOCACÃO -------------------

    @Transactional
    public void processarDevolucao(Pedido pedido) {
        for (PedidoItem item : pedido.getItens()) {
            Estoque estoque = estoqueRepository.findByProduto(item.getProduto())
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado para devolução: " + item.getProduto().getNome()));

            // Atualiza estoque
            estoque.setQuantidadeAtual(estoque.getQuantidadeAtual() + item.getQuantidade());
            estoqueRepository.save(estoque);

            // Cria registro de entrada
            EstoqueMovimento movimento = EstoqueMovimento.builder()
                    .produto(item.getProduto())
                    .tipoMovimento(TipoMovimento.ENTRADA)
                    .quantidade(item.getQuantidade())
                    .pedido(pedido)
                    .dataMovimento(LocalDateTime.now())
                    .build();
            estoqueMovimentoRepository.save(movimento);
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
    }

}
