package com.fernandodev.etiqueta;

import com.fernandodev.estoque.repository.EstoqueMovimentoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EtiquetaService {

    private final EstoqueMovimentoRepository repository;

    public EtiquetaService(EstoqueMovimentoRepository repository) {
        this.repository = repository;
    }

    public List<EtiquetaDTO> buscarEtiquetas(Long produtoId) {
        return repository.buscarEtiquetas(produtoId);
    }
}