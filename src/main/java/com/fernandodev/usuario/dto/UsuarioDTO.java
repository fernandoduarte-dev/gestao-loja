package com.fernandodev.usuario.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTO {

    private String nome;
    private String email;
    private String senha;

}
