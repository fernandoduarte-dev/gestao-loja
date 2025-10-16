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

        public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
            Produto produto = produtoConverter.paraProduto(produtoDTO);

            // Salva primeiro sem SKU para gerar o ID
            Produto salvo = produtoRepository.save(produto);


            if (salvo.getSku() == null || salvo.getSku().isBlank()) {
                String skuGerado = gerarSku(salvo);
                salvo.setSku(skuGerado);
                salvo = produtoRepository.save(salvo);
            }

            return produtoConverter.paraProdutoDTO(salvo);
        }


        // Gerar Sku automático
        private String gerarSku(Produto produto) {
            // Nome: pega até 3 letras da primeira palavra
            String nome = (produto.getNome() != null && !produto.getNome().isBlank())
                    ? produto.getNome().split(" ")[0].toUpperCase().substring(0, Math.min(3, produto.getNome().split(" ")[0].length()))
                    : "PRD";

            // Tecido: pega até 3 letras
            String tecido = (produto.getTecido() != null && !produto.getTecido().isBlank())
                    ? produto.getTecido().toUpperCase().substring(0, Math.min(3, produto.getTecido().length()))
                    : "TEC";

            // Cor: pega até 2 letras
            String cor = (produto.getCor() != null && !produto.getCor().isBlank())
                    ? produto.getCor().toUpperCase().substring(0, Math.min(2, produto.getCor().length()))
                    : "XX";

            // Tamanho: usa direto
            String tamanho = (produto.getTamanho() != null && !produto.getTamanho().isBlank())
                    ? produto.getTamanho().toUpperCase()
                    : "UN";

            return nome + "-" + tecido + "-" + cor + "-" + tamanho + "-" + String.format("%05d", produto.getId());
        }


        public List<ProdutoDTO> buscarProdutos(String sku, String tamanho, String cor, String tecido) {

            List<Produto> produtos;

            if (sku != null && !sku.isBlank()) {
                produtos = produtoRepository.findBySkuContainingIgnoreCase(sku);

            } else if (tamanho != null && cor != null) {
                produtos = produtoRepository.findByTamanhoAndCorContainingIgnoreCase(tamanho, cor);

            } else if (tamanho != null) {
                produtos = produtoRepository.findByTamanhoContainingIgnoreCase(tamanho);

            } else if (cor != null) {
                produtos = produtoRepository.findByCorContainingIgnoreCase(cor);

            } else if (tecido != null) {
                produtos = produtoRepository.findByTecidoContainingIgnoreCase(tecido);

            } else {
                produtos = produtoRepository.findAll();
            }

            return produtos.stream()
                    .map(produtoConverter::paraProdutoDTO)
                    .toList();
        }

        public void deletaProduto(Long id) {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            produtoRepository.delete(produto);
        }



    }





