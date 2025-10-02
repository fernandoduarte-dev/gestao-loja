package com.fernandodev.estoque.service;

import com.fernandodev.estoque.dto.EstoqueDTO;
import com.fernandodev.estoque.entity.Estoque;
import com.fernandodev.estoque.repository.EstoqueRepository;
import com.fernandodev.infrastructure.converter.ProdutoConverter;
import com.fernandodev.infrastructure.enums.StatusEstoque;
import com.fernandodev.infrastructure.etiqueta.ProdutoEtiqueta;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoConverter produtoConverter;

    public EstoqueDTO adicionarEstoque(Long produtoId, int quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se já existe estoque para esse produto
        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElse(Estoque.builder()
                        .produto(produto)
                        .quantidade(0)
                        .status(StatusEstoque.DISPONIVEL) // novo registro sempre começa disponível
                        .build());

        // Atualiza quantidade e status
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        estoque.setStatus(StatusEstoque.DISPONIVEL); // sempre DISPONÍVEL quando adiciona

        // Salva no banco
        estoque = estoqueRepository.save(estoque);

        // Gerar etiquetas (opcional, continua igual)
        String pasta = "arquivos-etiquetas";
        File diretorio = new File(pasta);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String nomeArquivo = pasta + "/etiquetas_" + produto.getSku() + "_" + estoque.getId() + "_" + timestamp + ".pdf";
        ProdutoEtiqueta.gerarEtiquetas(produto.getSku(), quantidade, nomeArquivo);

        // Retorna DTO
        return EstoqueDTO.builder()
                .id(estoque.getId())
                .produto(produtoConverter.paraProdutoDTO(produto))
                .quantidade(estoque.getQuantidade())
                .status(estoque.getStatus())
                .build();
    }


    public List<EstoqueDTO> listarEstoques() {
        return estoqueRepository.findAll()
                .stream()
                .map(estoque -> EstoqueDTO.builder()
                        .id(estoque.getId())
                        .produto(produtoConverter.paraProdutoDTO(estoque.getProduto()))
                        .quantidade(estoque.getQuantidade())
                        .status(estoque.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public EstoqueDTO buscarPorProduto(Long produtoId) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto " + produtoId));

        return EstoqueDTO.builder()
                .id(estoque.getId())
                .produto(produtoConverter.paraProdutoDTO(estoque.getProduto()))
                .quantidade(estoque.getQuantidade())
                .status(estoque.getStatus())
                .build();
    }
}
