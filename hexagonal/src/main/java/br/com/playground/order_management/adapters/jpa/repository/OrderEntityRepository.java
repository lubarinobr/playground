package br.com.playground.order_management.adapters.jpa.repository;

import br.com.playground.order_management.adapters.jpa.entity.OrderEntity;
import br.com.playground.order_management.domain.valueobject.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, OrderId> {
}
