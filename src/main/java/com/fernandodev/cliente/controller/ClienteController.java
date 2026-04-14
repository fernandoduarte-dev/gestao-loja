package com.fernandodev.cliente.controller;

import com.fernandodev.cliente.dto.ClienteDTO;
import com.fernandodev.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // Criar cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> cadastraCliente(@RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(clienteService.cadastraCliente(clienteDTO));
    }

    // Buscar cliente por email
    @GetMapping("/email")
    public ResponseEntity<ClienteDTO> buscarClientePorEmail(@RequestParam("email") String email) {
        ClienteDTO cliente = clienteService.buscarClientePorEmail(email);
        return ResponseEntity.ok(cliente);
    }

    // Deletar cliente por email
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarClientePorEmail(@PathVariable String email) {
        clienteService.deletaClientePorEmail(email);
        return ResponseEntity.noContent().build();
    }

    // Buscar clientes por nome
    @GetMapping("/nome")
    public ResponseEntity<List<ClienteDTO>> buscarClientePorNome(@RequestParam ("nome") String nome) {
        List<ClienteDTO> clientes = clienteService.buscarClientesPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    // Buscar clientes por cidade
    @GetMapping("/cidade")
    public ResponseEntity<List<ClienteDTO>> buscarClientePorCidade(@RequestParam ("cidade") String cidade) {
        List<ClienteDTO> clientesCity = clienteService.buscarClientesPorCidade(cidade);
        return ResponseEntity.ok(clientesCity);
    }
}