package com.fernandodev.estoque.entity;

import com.fernandodev.infrastructure.enums.TipoMovimento;
import com.fernandodev.produto.entity.Produto;
import com.fernandodev.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "estoque_movimento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueMovimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Produto produto;

    private String tamanho;

    // + entra / - sai
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoMovimento tipoMovimento;

    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}