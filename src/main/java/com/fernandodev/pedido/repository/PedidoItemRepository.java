package com.fernandodev.pedido.repository;

import com.fernandodev.pedido.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

}
