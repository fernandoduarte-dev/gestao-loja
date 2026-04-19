package com.fernandodev.estoque.repository;

import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstoqueMovimentoRepository extends JpaRepository<EstoqueMovimento, Long> {

    // 🔹 Histórico geral
    @Query("""
SELECT e FROM EstoqueMovimento e
LEFT JOIN FETCH e.produtoItem pi
LEFT JOIN FETCH pi.produto
LEFT JOIN FETCH e.usuario
ORDER BY e.data DESC
""")
    List<EstoqueMovimento> buscarHistorico();

    // 🔹 Movimentos por PRODUTO (via ProdutoItem)
    @Query("""
        SELECT em
        FROM EstoqueMovimento em
        WHERE em.produtoItem.produto.id = :produtoId
        ORDER BY em.data DESC
    """)
    List<EstoqueMovimento> buscarPorProduto(@Param("produtoId") Long produtoId);

    // 🔹 Movimentos por ITEM específico (SKU)
    @Query("""
        SELECT em
        FROM EstoqueMovimento em
        WHERE em.produtoItem.id = :itemId
        ORDER BY em.data DESC
    """)
    List<EstoqueMovimento> buscarPorItem(@Param("itemId") Long itemId);

    // 🔹 Movimentos por tipo (entrada / saída)
    List<EstoqueMovimento> findByTipoMovimentoOrderByDataDesc(TipoMovimento tipoMovimento);

    // 🔹 Movimentos por usuário
    @Query("""
        SELECT em
        FROM EstoqueMovimento em
        WHERE em.usuario.id = :usuarioId
        ORDER BY em.data DESC
    """)
    List<EstoqueMovimento> buscarPorUsuario(@Param("usuarioId") Long usuarioId);
}

