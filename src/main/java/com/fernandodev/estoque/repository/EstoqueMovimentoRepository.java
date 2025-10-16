package com.fernandodev.estoque.repository;

import com.fernandodev.estoque.entity.EstoqueMovimento;
import com.fernandodev.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstoqueMovimentoRepository extends JpaRepository<EstoqueMovimento, Long> {
    List<EstoqueMovimento> findByProduto(Produto produto);
}