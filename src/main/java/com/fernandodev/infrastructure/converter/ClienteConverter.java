package com.fernandodev.infrastructure.converter;

import com.fernandodev.cliente.dto.ClienteDTO;
import com.fernandodev.cliente.dto.TelefoneDTO;
import com.fernandodev.cliente.entity.Cliente;
import com.fernandodev.cliente.entity.Telefone;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ClienteConverter {

    // Converte DTO → Entidade
    public Cliente paraCliente(ClienteDTO clienteDTO) {
        Cliente cliente = Cliente.builder()
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .rua(clienteDTO.getRua())
                .numero(clienteDTO.getNumero())
                .complemento(clienteDTO.getComplemento())
                .cidade(clienteDTO.getCidade())
                .estado(clienteDTO.getEstado())
                .cep(clienteDTO.getCep())
                .email(clienteDTO.getEmail())
                .build();

        List<TelefoneDTO> telefonesDTO = Optional.ofNullable(clienteDTO.getTelefones())
                .orElse(Collections.emptyList());

        cliente.setTelefones(
                telefonesDTO.stream()
                        .map(t -> Telefone.builder()
                                .ddd(t.getDdd())
                                .numero(t.getNumero())
                                .cliente(cliente)
                                .build())
                        .toList()
        );

        return cliente;
    }

    // Converte Entidade → DTO
    public ClienteDTO paraClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .rua(cliente.getRua())
                .numero(cliente.getNumero())
                .complemento(cliente.getComplemento())
                .cidade(cliente.getCidade())
                .estado(cliente.getEstado())
                .cep(cliente.getCep())
                .email(cliente.getEmail())
                .telefones(Optional.ofNullable(cliente.getTelefones())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(t -> TelefoneDTO.builder()
                                .ddd(t.getDdd())
                                .numero(t.getNumero())
                                .build())
                        .toList()
                )
                .build();
    }

    // Atualiza entidade com dados do DTO
    public Cliente updateCliente(ClienteDTO dto, Cliente entity) {
        Optional.ofNullable(dto.getNome()).ifPresent(entity::setNome);
        Optional.ofNullable(dto.getCpf()).ifPresent(entity::setCpf);
        Optional.ofNullable(dto.getEmail()).ifPresent(entity::setEmail);
        Optional.ofNullable(dto.getRua()).ifPresent(entity::setRua);
        Optional.ofNullable(dto.getNumero()).ifPresent(entity::setNumero);
        Optional.ofNullable(dto.getComplemento()).ifPresent(entity::setComplemento);
        Optional.ofNullable(dto.getCidade()).ifPresent(entity::setCidade);
        Optional.ofNullable(dto.getEstado()).ifPresent(entity::setEstado);
        Optional.ofNullable(dto.getCep()).ifPresent(entity::setCep);

        if (dto.getTelefones() != null) {
            entity.getTelefones().clear();
            entity.getTelefones().addAll(
                    dto.getTelefones().stream()
                            .map(t -> Telefone.builder()
                                    .ddd(t.getDdd())
                                    .numero(t.getNumero())
                                    .cliente(entity)
                                    .build())
                            .toList()
            );
        }

        return entity;
    }
}
