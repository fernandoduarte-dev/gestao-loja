package com.fernandodev.infrastructure.converter;


import com.fernandodev.cliente.dto.ClienteDTO;
import com.fernandodev.cliente.entity.Cliente;
import org.springframework.stereotype.Component;

@Component

public class ClienteConverter {

    public Cliente paraCliente(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .telefone(clienteDTO.getTelefone())
                .rua(clienteDTO.getRua())
                .numero(clienteDTO.getNumero())
                .complemento(clienteDTO.getComplemento())
                .cidade(clienteDTO.getCidade())
                .estado(clienteDTO.getEstado())
                .cep(clienteDTO.getCep())


                .build();
    }



    public ClienteDTO paraClienteDTO(Cliente clienteDTO) {
        return ClienteDTO.builder()
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .telefone(clienteDTO.getTelefone())
                .rua(clienteDTO.getRua())
                .numero(clienteDTO.getNumero())
                .complemento(clienteDTO.getComplemento())
                .cidade(clienteDTO.getCidade())
                .estado(clienteDTO.getEstado())
                .cep(clienteDTO.getCep())
                .build();
    }



    public Cliente updateCliente(ClienteDTO clienteDTO, Cliente entity) {
        return Cliente.builder()
                .nome(clienteDTO.getNome() != null ? clienteDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .cpf(clienteDTO.getCpf() != null ? clienteDTO.getCpf() : entity.getCpf())
                .telefone(clienteDTO.getTelefone() != null ? clienteDTO.getTelefone() : entity.getTelefone())
                .email(clienteDTO.getEmail() != null ? clienteDTO.getEmail() : entity.getEmail())
                .rua(clienteDTO.getRua()!= null ? clienteDTO.getRua() : entity.getRua())
                .numero(clienteDTO.getNumero()!= null ? clienteDTO.getNumero() : entity.getNumero())
                .complemento(clienteDTO.getComplemento()!= null ? clienteDTO.getComplemento() : entity.getComplemento())
                .cidade(clienteDTO.getCidade()!= null ? clienteDTO.getCidade() : entity.getCidade())
                .estado(clienteDTO.getEstado()!= null ? clienteDTO.getEstado() : entity.getEstado())
                .cep(clienteDTO.getCep()!= null ? clienteDTO.getCep() : entity.getCep())
                .build();
    }


}
