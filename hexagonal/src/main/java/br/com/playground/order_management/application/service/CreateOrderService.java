package br.com.playground.order_management.application.service;

import br.com.playground.order_management.application.ports.in.CreateOrderUseCase;
import br.com.playground.order_management.domain.model.Order;
import br.com.playground.order_management.domain.ports.OrderRepository;
import br.com.playground.order_management.domain.valueobject.OrderId;
import br.com.playground.order_management.domain.valueobject.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateOrderService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;

    public CreateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public UUID handle(BigDecimal amount) {
        var order = Order
                .builder()
                .orderId(new OrderId(UUID.randomUUID()))
                .status(OrderStatus.CREATED)
                .total(amount)
                .build();

        orderRepository.save(order);
        return order.getOrderId().id();
    }
}
