package com.fernandodev.pedido.controller;

import com.fernandodev.infrastructure.converter.PedidoConverter;
import com.fernandodev.pedido.dto.PedidoDTO;
import com.fernandodev.pedido.entity.Pedido;
import com.fernandodev.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    // -------------------- CRIAR PEDIDO --------------------
    @PostMapping("/locacao")
    public ResponseEntity<PedidoDTO> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.gerarTipoPedido(pedidoDTO); // método da service
        PedidoDTO respostaDTO = PedidoConverter.paraPedidoDTO(pedido);
        return ResponseEntity.ok(respostaDTO);
    }

    // -------------------- DEVOLVER PEDIDO (LOCACAO) --------------------
    @PostMapping("/{pedidoId}/devolver")
    public ResponseEntity<PedidoDTO> devolverPedido(@PathVariable Long pedidoId) {
        // usa método público da service para buscar o pedido
        Pedido pedido = pedidoService.buscarPorId(pedidoId);

        pedidoService.processarDevolucao(pedido); // processa devolução e atualiza estoque
        PedidoDTO respostaDTO = PedidoConverter.paraPedidoDTO(pedido);
        return ResponseEntity.ok(respostaDTO);
    }


}
