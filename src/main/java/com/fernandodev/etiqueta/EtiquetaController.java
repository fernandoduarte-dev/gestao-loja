package com.fernandodev.etiqueta;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

    private final EtiquetaService etiquetaService;


    public EtiquetaController(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @GetMapping
    public List<EtiquetaDTO> listar(@RequestParam(required = false) Long produtoId) {
        return etiquetaService.buscarEtiquetas(produtoId);
    }
}
