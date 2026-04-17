package com.fernandodev.estoque.service;

import com.fernandodev.estoque.dto.EstoqueMovimentoDTO;
import com.fernandodev.estoque.dto.SaldoProjection;
import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.estoque.repository.EstoqueMovimentoRepository;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.produto.repository.ProdutoRepository;
import com.fernandodev.usuario.entity.Usuario;
import com.fernandodev.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueMovimentoService {

    private final EstoqueMovimentoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    // 🔹 ENTRADA
    @Transactional
    public void entrada(Long produtoId, String tamanho, Integer quantidade) {

        validarQuantidade(quantidade);

        Produto produto = buscarProduto(produtoId);

        Usuario usuario = buscarUsuarioLogado(); // 👈 AQUI

        EstoqueMovimento movimento = EstoqueMovimento.builder()
                .produto(produto)
                .tamanho(tamanho)
                .quantidade(quantidade)
                .tipoMovimento(TipoMovimento.ENTRADA)
                .data(LocalDateTime.now())
                .usuario(usuario)
                .build();

        repository.save(movimento);
    }

    // 🔹 SAÍDA (com validação de estoque)
    @Transactional
    public void saida(Long produtoId, String tamanho, Integer quantidade) {

        validarQuantidade(quantidade);

        Long saldoAtual = repository.calcularSaldo(produtoId, tamanho);

        if (saldoAtual < quantidade) {
            throw new RuntimeException("Estoque insuficiente. Saldo atual: " + saldoAtual);
        }

        Produto produto = buscarProduto(produtoId);

        Usuario usuario = buscarUsuarioLogado(); // 👈

        EstoqueMovimento movimento = EstoqueMovimento.builder()
                .produto(produto)
                .tamanho(tamanho)
                .quantidade(quantidade)
                .tipoMovimento(TipoMovimento.SAIDA)
                .data(LocalDateTime.now())
                .usuario(usuario)
                .build();

        repository.save(movimento);
    }
    // 🔹 SALDO
    @Transactional(readOnly = true)
    public Long saldo(Long produtoId, String tamanho) {
        return repository.calcularSaldo(produtoId, tamanho);
    }



    // =========================
    // 🔧 MÉTODOS AUXILIARES
    // =========================

    private Produto buscarProduto(Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new RuntimeException("Quantidade inválida");
        }
    }

    private EstoqueMovimentoDTO toDTO(EstoqueMovimento e) {
        return new EstoqueMovimentoDTO(
                e.getId(),
                e.getProduto().getId(),
                e.getProduto().getNome(),
                e.getTamanho(),
                e.getQuantidade(),
                e.getTipoMovimento(),
                e.getData(),
                e.getUsuario() != null ? e.getUsuario().getNome() : null
        );
    }

    @Transactional(readOnly = true)
    public List<SaldoProjection> buscarSaldos(
            Long idProduto,
            String tamanho,
            String tecido,
            String cor
    ) {
        return repository.buscarSaldos(idProduto, tamanho, tecido, cor);
    }

    @Transactional(readOnly = true)
    public List<EstoqueMovimentoDTO> historico() {

        return repository.buscarHistorico()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private Usuario buscarUsuarioLogado() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}