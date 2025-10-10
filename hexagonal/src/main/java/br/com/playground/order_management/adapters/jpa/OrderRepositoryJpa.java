package br.com.playground.order_management.adapters.jpa;

import br.com.playground.order_management.adapters.jpa.mapper.OrderJpaMapper;
import br.com.playground.order_management.adapters.jpa.repository.OrderEntityRepository;
import br.com.playground.order_management.domain.model.Order;
import br.com.playground.order_management.domain.ports.OrderRepository;
import br.com.playground.order_management.domain.valueobject.OrderId;
import br.com.playground.order_management.domain.valueobject.OrderNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRepositoryJpa implements OrderRepository {

    private final OrderEntityRepository repository;
    private final OrderJpaMapper orderJpaMapper;

    @Override
    public void save(Order order) {
        repository.save(orderJpaMapper.toEntity(order));
    }

    @Override
    public Order findById(OrderId orderId) {
        return null;
    }

    @Override
    public Order findByOrderNumber(OrderNumber number) {
        return null;
    }
}
