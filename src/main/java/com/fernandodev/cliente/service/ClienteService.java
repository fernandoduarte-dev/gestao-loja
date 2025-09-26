package com.fernandodev.cliente.service;

import com.fernandodev.cliente.dto.ClienteDTO;
import com.fernandodev.cliente.entity.Cliente;
import com.fernandodev.cliente.repository.ClienteRepository;
import com.fernandodev.infrastructure.converter.ClienteConverter;
import com.fernandodev.infrastructure.exception.ConflictException;
import com.fernandodev.infrastructure.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor


public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteConverter clienteConverter;



    public ClienteDTO cadastraCliente(ClienteDTO clienteDTO) {
        emailExiste(clienteDTO.getEmail());
        Cliente cliente = clienteConverter.paraCliente(clienteDTO);
        cliente = clienteRepository.save(cliente);
        return clienteConverter.paraClienteDTO(cliente);
    }

    public void emailExiste(String email) {
        if (clienteRepository.existsByEmail(email)) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return clienteRepository.existsByEmail(email);

    }

    public ClienteDTO buscarClientePorEmail(String email) {
        return clienteConverter.paraClienteDTO(
                clienteRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + email))
        );
    }


    public void deletaClientePorEmail(String email) {
        clienteRepository.deleteByEmail(email);

    }

    public List<ClienteDTO> buscarClientesPorNome(String nome) {
        List<Cliente> clientes = clienteRepository.findByNome(nome);
        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum cliente encontrado com nome: " + nome);
        }
        return clientes.stream()
                .map(clienteConverter::paraClienteDTO)
                .toList();
    }








}