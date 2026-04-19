package com.fernandodev.estoque.service;

import com.fernandodev.estoque.dto.EstoqueMovimentoDTO;
import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.estoque.repository.EstoqueMovimentoRepository;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import com.fernandodev.produto.dto.ProdutoItemSaldoProjection;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.produto.entity.ProdutoItem;
import com.fernandodev.produto.repository.ProdutoItemRepository;
import com.fernandodev.produto.repository.ProdutoRepository;
import com.fernandodev.produto.service.SkuGenerator;
import com.fernandodev.usuario.entity.Usuario;
import com.fernandodev.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueMovimentoService {

    private final EstoqueMovimentoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoItemRepository produtoItemRepository;
    private final UsuarioRepository usuarioRepository;
    private final SkuGenerator skuGenerator;

    // 🔹 ENTRADA (cria peças reais no estoque)
    @Transactional
    public void entrada(Long produtoId, String tamanho, int quantidade) {

        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade inválida");
        }

        Produto produto = buscarProduto(produtoId);
        Usuario usuario = buscarUsuarioLogado();

        for (int i = 0; i < quantidade; i++) {

            ProdutoItem item = ProdutoItem.builder()
                    .produto(produto)
                    .tamanho(tamanho)
                    .disponivel(true)
                    .sku(
                            skuGenerator.gerar(
                                    produto.getNome(),
                                    produto.getTecido(),
                                    produto.getCor(),
                                    tamanho
                            )
                    )
                    .build();

            produtoItemRepository.save(item);

            EstoqueMovimento movimento = EstoqueMovimento.builder()
                    .produtoItem(item)
                    .tipoMovimento(TipoMovimento.ENTRADA)
                    .data(LocalDateTime.now())
                    .usuario(usuario)
                    .build();

            repository.save(movimento);
        }
    }

    // 🔻 SAÍDA (remove peças reais do estoque)
    @Transactional
    public void saida(Long produtoId, String tamanho, int quantidade) {

        if (quantidade <= 0) {
            throw new RuntimeException("Quantidade inválida");
        }

        Usuario usuario = buscarUsuarioLogado();

        List<ProdutoItem> itens = produtoItemRepository
                .buscarDisponiveis(produtoId, tamanho);

        if (itens.size() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        for (int i = 0; i < quantidade; i++) {

            ProdutoItem item = itens.get(i);

            item.setDisponivel(false);
            produtoItemRepository.save(item);

            EstoqueMovimento movimento = EstoqueMovimento.builder()
                    .produtoItem(item)
                    .tipoMovimento(TipoMovimento.SAIDA)
                    .data(LocalDateTime.now())
                    .usuario(usuario)
                    .build();

            repository.save(movimento);
        }
    }

    @Transactional
    public void saidaPorItens(List<Long> itensIds) {

        Usuario usuario = buscarUsuarioLogado();

        List<ProdutoItem> itens = produtoItemRepository.findAllById(itensIds);

        if (itens.size() != itensIds.size()) {
            throw new RuntimeException("Alguns itens não foram encontrados");
        }

        for (ProdutoItem item : itens) {

            if (!item.getDisponivel()) {
                throw new RuntimeException("Item já indisponível: " + item.getSku());
            }

            // 🔻 baixa o item
            item.setDisponivel(false);
            produtoItemRepository.save(item);

            // 🔻 cria movimento correto
            EstoqueMovimento mov = EstoqueMovimento.builder()
                    .produtoItem(item)
                    .tipoMovimento(TipoMovimento.SAIDA)
                    .data(LocalDateTime.now())
                    .usuario(usuario)
                    .build();

            repository.save(mov); // ✅ CORRETO
        }

    }

    // 🔹 SALDO (agora vem do ProdutoItem)
    @Transactional(readOnly = true)
    public Long saldo(Long produtoId, String tamanho) {
        return produtoItemRepository.contarDisponiveis(produtoId, tamanho);
    }

    // 🔹 HISTÓRICO
    @Transactional(readOnly = true)
    public List<EstoqueMovimentoDTO> historico() {

        return repository.buscarHistorico()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // =========================
    // 🔧 AUXILIARES
    // =========================

    private Produto buscarProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private Usuario buscarUsuarioLogado() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private EstoqueMovimentoDTO toDTO(EstoqueMovimento e) {

        return new EstoqueMovimentoDTO(
                e.getId(),

                // Produto
                e.getProdutoItem() != null ? e.getProdutoItem().getProduto().getId() : null,
                e.getProdutoItem() != null ? e.getProdutoItem().getProduto().getNome() : null,

                // Produto Item
                e.getProdutoItem() != null ? e.getProdutoItem().getId() : null,


                // Tipo movimento
                e.getTipoMovimento(),

                // Data
                e.getData(),

                // Usuário
                e.getUsuario() != null ? e.getUsuario().getNome() : null,

                // 🔥 TAMANHO (CORREÇÃO)
                e.getProdutoItem() != null ? e.getProdutoItem().getTamanho() : null
        );
    }

    @Transactional(readOnly = true)
    public List<ProdutoItemSaldoProjection> buscarSaldos(
            Long idProduto,
            String tamanho,
            String tecido,
            String cor
    ) {
        return produtoItemRepository.buscarSaldos(
                idProduto,
                tamanho,
                tecido,
                cor
        );
    }
}