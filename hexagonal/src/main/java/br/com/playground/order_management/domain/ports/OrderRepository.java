package br.com.playground.order_management.domain.ports;

import br.com.playground.order_management.domain.model.Order;
import br.com.playground.order_management.domain.valueobject.OrderId;
import br.com.playground.order_management.domain.valueobject.OrderNumber;

public interface OrderRepository {

    void save(Order order);
    Order findById(OrderId orderId);
    Order findByOrderNumber(OrderNumber number);
}
