package com.fernandodev.produto.repository;

import com.fernandodev.produto.entity.ProdutoItem;
import com.fernandodev.produto.dto.ProdutoItemSaldoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoItemRepository extends JpaRepository<ProdutoItem, Long> {

    // 🔹 Itens disponíveis por produto + tamanho
    @Query("""
        SELECT pi
        FROM ProdutoItem pi
        WHERE pi.produto.id = :produtoId
          AND pi.tamanho = :tamanho
          AND pi.disponivel = true
    """)
    List<ProdutoItem> buscarDisponiveis(
            @Param("produtoId") Long produtoId,
            @Param("tamanho") String tamanho
    );

    // 🔹 Contar saldo disponível
    @Query("""
        SELECT COUNT(pi.id)
        FROM ProdutoItem pi
        WHERE pi.produto.id = :produtoId
          AND pi.tamanho = :tamanho
          AND pi.disponivel = true
    """)
    Long contarDisponiveis(
            @Param("produtoId") Long produtoId,
            @Param("tamanho") String tamanho
    );

    // 🔹 Todos os itens de um produto
    @Query("""
        SELECT pi
        FROM ProdutoItem pi
        WHERE pi.produto.id = :produtoId
    """)
    List<ProdutoItem> buscarTodosPorProduto(
            @Param("produtoId") Long produtoId
    );

    // 🔹 Itens indisponíveis (alugados / ocupados)
    @Query("""
        SELECT pi
        FROM ProdutoItem pi
        WHERE pi.produto.id = :produtoId
          AND pi.disponivel = false
    """)
    List<ProdutoItem> buscarIndisponiveis(
            @Param("produtoId") Long produtoId
    );

    // 🔹 Resumo de estoque (saldo agrupado)
    @Query("""
        SELECT 
            pi.produto.id AS produtoId,
            pi.produto.nome AS produtoNome,
            pi.produto.tecido AS produtoTecido,
            pi.produto.cor AS produtoCor,
            pi.tamanho AS tamanho,
            COUNT(pi.id) AS saldo
        FROM ProdutoItem pi
        WHERE pi.disponivel = true
          AND (:produtoId IS NULL OR pi.produto.id = :produtoId)
          AND (:tamanho IS NULL OR pi.tamanho = :tamanho)
          AND (:tecido IS NULL OR pi.produto.tecido = :tecido)
          AND (:cor IS NULL OR pi.produto.cor = :cor)
        GROUP BY 
            pi.produto.id,
            pi.produto.nome,
            pi.produto.tecido,
            pi.produto.cor,
            pi.tamanho
    """)
    List<ProdutoItemSaldoProjection> buscarSaldos(
            @Param("produtoId") Long produtoId,
            @Param("tamanho") String tamanho,
            @Param("tecido") String tecido,
            @Param("cor") String cor
    );

    @Query("""
    SELECT COUNT(pi)
    FROM ProdutoItem pi
    WHERE pi.produto.id = :produtoId
""")
    Long contarTotalPorProduto(@Param("produtoId") Long produtoId);

    List<ProdutoItem> findByProdutoIdAndDisponivelTrue(Long produtoId);

    @Query("""
    SELECT pi
    FROM ProdutoItem pi
    WHERE pi.produto.id = :produtoId
      AND (:tamanho IS NULL OR pi.tamanho = :tamanho)
      AND pi.disponivel = true
""")
    List<ProdutoItem> findDisponiveisByProdutoAndTamanho(
            @Param("produtoId") Long produtoId,
            @Param("tamanho") String tamanho
    );


}

