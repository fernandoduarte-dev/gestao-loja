package com.fernandodev.estoque.repository;

import com.fernandodev.estoque.entity.Estoque;
import com.fernandodev.infrastructure.enums.StatusEstoque;
import com.fernandodev.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // Buscar estoque por produto
    Optional<Estoque> findByProduto(Produto produto);

    List<Estoque> findByProdutoNomeContaining(String nome);

    Optional<Estoque> findByProdutoId(Long produtoId);



}
