package com.fernandodev.cliente.repository;

import com.fernandodev.cliente.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    // Buscar telefones por número exato
    List<Telefone> findByNumero(String numero);

    // Buscar telefones que contenham determinada sequência
    List<Telefone> findByNumeroContaining(String parteNumero);

    // Buscar todos os telefones de um cliente específico
    List<Telefone> findByClienteId(Long clienteId);

    // Buscar telefones por DDD
    List<Telefone> findByDdd(String ddd);

    // Buscar telefones por DDD e número
    List<Telefone> findByDddAndNumero(String ddd, String numero);
}
