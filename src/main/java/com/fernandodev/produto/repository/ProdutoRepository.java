package com.fernandodev.produto.repository;

import com.fernandodev.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findBySkuContainingIgnoreCase(String sku);
    List<Produto> findByTamanhoContainingIgnoreCase(String tamanho);
    List<Produto> findByCorContainingIgnoreCase(String cor);
    List<Produto> findByTamanhoAndCorContainingIgnoreCase(String tamanho, String cor);
    List<Produto> findByTecidoContainingIgnoreCase(String tecido);


    Optional<Produto> findBySku(String sku);
    Optional<Produto> findByNome(String nome);
}

