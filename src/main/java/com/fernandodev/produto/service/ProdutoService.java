package com.fernandodev.produto.service;

import com.fernandodev.infrastructure.converter.ProdutoConverter;
import com.fernandodev.produto.dto.ProdutoDTO;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoConverter produtoConverter;

    // ✅ SALVAR PRODUTO (SEM SKU, SEM TAMANHO)
    public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
        Produto produto = produtoConverter.paraProduto(produtoDTO);

        Produto salvo = produtoRepository.save(produto);

        return produtoConverter.paraProdutoDTO(salvo);
    }

    // ✅ BUSCA SOMENTE POR DADOS DO PRODUTO
    public List<ProdutoDTO> buscarProdutos(String nome, String cor, String tecido) {

        List<Produto> produtos;

        if (nome != null && !nome.isBlank()) {
            produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);

        } else if (cor != null && !cor.isBlank()) {
            produtos = produtoRepository.findByCorContainingIgnoreCase(cor);

        } else if (tecido != null && !tecido.isBlank()) {
            produtos = produtoRepository.findByTecidoContainingIgnoreCase(tecido);

        } else {
            produtos = produtoRepository.findAll();
        }

        return produtos.stream()
                .map(produtoConverter::paraProdutoDTO)
                .toList();
    }

    // ✅ BUSCAR POR ID (vai ser útil pro estoque depois)
    public ProdutoDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return produtoConverter.paraProdutoDTO(produto);
    }

    // ✅ DELETAR PRODUTO
    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }
}