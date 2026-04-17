package com.fernandodev.produto.repository;

import com.fernandodev.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByCorContainingIgnoreCase(String cor);
    List<Produto> findByTecidoContainingIgnoreCase(String tecido);
}

