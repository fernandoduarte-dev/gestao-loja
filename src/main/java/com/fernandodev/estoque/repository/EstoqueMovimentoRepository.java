package com.fernandodev.estoque.repository;

import com.fernandodev.estoque.dto.SaldoDTO;
import com.fernandodev.estoque.dto.SaldoProjection;
import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.infrastructure.enums.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EstoqueMovimentoRepository extends JpaRepository<EstoqueMovimento, Long> {

    @Query("""
    SELECT em
    FROM EstoqueMovimento em
    JOIN em.usuario u
    ORDER BY em.data DESC
""")
    List<EstoqueMovimento> buscarHistorico();

    @Query("""
        SELECT COALESCE(
            SUM(
                CASE e.tipoMovimento
                    WHEN com.fernandodev.infrastructure.enums.TipoMovimento.ENTRADA THEN e.quantidade
                    ELSE -e.quantidade
                END
            ), 0
        )
        FROM EstoqueMovimento e
        WHERE e.produto.id = :produtoId
        AND e.tamanho = :tamanho
    """)
    Long calcularSaldo(
            @Param("produtoId") Long produtoId,
            @Param("tamanho") String tamanho
    );

    @Query("""
    SELECT 
        e.produto.id AS produtoId,
        e.produto.nome AS produtoNome,
        e.produto.tecido AS produtoTecido,
        e.produto.cor AS produtoCor,
        e.tamanho AS tamanho,
        COALESCE(SUM(
            CASE 
                WHEN e.tipoMovimento = com.fernandodev.infrastructure.enums.TipoMovimento.ENTRADA
                THEN e.quantidade
                ELSE -e.quantidade
            END
        ), 0) AS saldo
    FROM EstoqueMovimento e
    WHERE (:idProduto IS NULL OR e.produto.id = :idProduto)
      AND (:tamanho IS NULL OR e.tamanho = :tamanho)
      AND (:tecido IS NULL OR e.produto.tecido ILIKE CONCAT('%', :tecido, '%'))
      AND (:cor IS NULL OR e.produto.cor ILIKE CONCAT('%', :cor, '%'))
    GROUP BY 
        e.produto.id, 
        e.produto.nome, 
        e.produto.tecido,
        e.produto.cor,
        e.tamanho
""")
    List<SaldoProjection> buscarSaldos(
            @Param("idProduto") Long idProduto,
            @Param("tamanho") String tamanho,
            @Param("tecido") String tecido,
            @Param("cor") String cor
    );

}